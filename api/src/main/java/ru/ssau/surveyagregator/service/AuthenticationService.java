package ru.ssau.surveyagregator.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ssau.surveyagregator.model.*;
import ru.ssau.surveyagregator.repository.SurveyRepository;
import ru.ssau.surveyagregator.repository.TokenRepository;
import ru.ssau.surveyagregator.repository.UserRepository;
import ru.ssau.surveyagregator.requests.LoginRequestDto;
import ru.ssau.surveyagregator.requests.RegistrationRequestDto;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.requests.UserUpdateRequest;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.responses.UserProfileResponse;
import ru.ssau.surveyagregator.responses.UserSurveyResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final SurveyService surveyService;

    private final SurveyRepository surveyRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final UserServiceImpl userServiceImpl;


    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository,
                                 SurveyService surveyService,
                                 UserServiceImpl userServiceImpl,
                                 SurveyRepository surveyRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.surveyService = surveyService;
        this.userServiceImpl = userServiceImpl;
        this.surveyRepository = surveyRepository;
    }

    public void register(RegistrationRequestDto request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = userRepository.save(user);

    }

    public AuthenticationResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        revokeAllToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponseDto(accessToken, refreshToken);
    }

    private void revokeAllToken(User user) {

        List<Token> validTokens = tokenRepository.findAllAccessTokenByUser(user.getId());

        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {

        Token token = new Token();

        token.setAccessToken(accessToken);

        token.setRefreshToken(refreshToken);

        token.setLoggedOut(false);

        token.setUser(user);

        tokenRepository.save(token);
    }

    public ResponseEntity<AuthenticationResponseDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        if (jwtService.isValidRefresh(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<>(new AuthenticationResponseDto(accessToken, refreshToken), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<String> updatePassword(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 UserUpdateRequest uureq) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            uureq.getOldPassword()
                    )
            );
            if (jwtService.isValid(token, user)) {
                user.setPassword(passwordEncoder.encode(uureq.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("Пароль успешно изменён");
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        } catch (AuthenticationException | SignatureException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> userInfo(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        return ResponseEntity.ok(new UserProfileResponse(user.getEmail(), user.getUsername(), user.getId().toString(),surveyService.findSurveys(user.getId())));
    }

    public ResponseEntity<?> createSurvey(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody SurveyFormRequest survey
    ) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        List<User> users = userServiceImpl.findAllById(survey.getUserIds());
        users.add(user);
        surveyService.createSurvey(survey, users);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<?> findUserSurvey(
            HttpServletRequest request,
            HttpServletResponse response,
            UUID id
    ) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        List<UUID> surveyIds = surveyService.findSurveysIds(user.getId());

        if (surveyIds.contains(id)) {

            Survey survey = surveyRepository.findById(id).get();
            UserSurveyResponse surveyResponse = new UserSurveyResponse();
            surveyResponse.setTitle(survey.getSurveyTitle());
            surveyResponse.setDescription(survey.getSurveyDescription());
            List<UserSurveyResponse.QuestionResponse> questionResponses = new ArrayList<>();
            for (Question question : survey.getQuestions()) {
                List<UserSurveyResponse.AnswerResponse> answerResponses = new ArrayList<>();
                UserSurveyResponse.QuestionResponse questionResponse = new UserSurveyResponse.QuestionResponse();
                questionResponse.setQuestionText(question.getQuestionText());
                for (Answer answer : question.getAnswers()) {
                    UserSurveyResponse.AnswerResponse answerResponse = new UserSurveyResponse.AnswerResponse(answer.getAnswerText(), answer.getAnswerQuantity());
                    answerResponses.add(answerResponse);
                }
                questionResponse.setAnswers(answerResponses);
                questionResponses.add(questionResponse);
            }
            surveyResponse.setQuestions(questionResponses);
            return ResponseEntity.ok(surveyResponse);
        }
        else {
            throw new AccessDeniedException("403 returned");
        }
    }

    public ResponseEntity<?> accessToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authorizationHeader.substring(7);

            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok("");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
