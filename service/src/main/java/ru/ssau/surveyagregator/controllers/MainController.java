package ru.ssau.surveyagregator.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.client.SurveyAgregatorRestClientImpl;
import ru.ssau.surveyagregator.requests.*;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.responses.SurveyResponse;
import ru.ssau.surveyagregator.responses.UserProfileResponse;
import ru.ssau.surveyagregator.responses.UserSurveyResponse;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private SurveyAgregatorRestClientImpl surveyAgregatorRestClientImpl;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registrationSuccess(Model model, @RequestBody RegistrationRequestDto request) {
        String response = surveyAgregatorRestClientImpl.sendUserRegistration(request);
        if (Objects.equals(response, "email") || Objects.equals(response, "username")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> loginSuccess(Model model, @RequestBody LoginRequestDto request) {
        AuthenticationResponseDto response = surveyAgregatorRestClientImpl.getTokens(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public String user(Model model) {
        return "user";
    }

    @PostMapping("/user")
    public ResponseEntity<UserProfileResponse> userInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
        UserProfileResponse user = surveyAgregatorRestClientImpl.getUserInfo(request, response);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/survey")
    public String userSurvey(Model model) {
        return "user_survey";
    }

    @PostMapping("/user/survey")
    public ResponseEntity<UserSurveyResponse> userSurveyInfo(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam UUID id) throws AccessDeniedException {
        try {
            UserSurveyResponse survey = surveyAgregatorRestClientImpl.getUserSurvey(id, request, response);
            return ResponseEntity.ok(survey);
        } catch (RuntimeException e) {
            // Обрабатываем любое исключение, которое нарушает доступ
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }

    @PostMapping("/logout")
    public String logoutConfirm(Model model, HttpServletRequest request, HttpServletResponse response) {
        surveyAgregatorRestClientImpl.logoutTokens(request, response);
        return "login";
    }

    @GetMapping("/survey")
    public String survey(Model model, @RequestParam UUID id) {
        SurveyResponse surveyResponse = surveyAgregatorRestClientImpl.getSurvey(id);
        if (surveyResponse != null) {
            model.addAttribute("survey", surveyResponse);
            return "survey";
        } else
            return "error/404";
    }

    @PostMapping("/survey")
    public String submitSurvey(Model model, @RequestBody AnswerRequest answer) {
        String response = surveyAgregatorRestClientImpl.sendAnswer(answer);
        return "registration";
    }

    @GetMapping("/user/create")
    public String createSurvey(Model model) {
        return "create_survey";
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> sendSurvey(Model model, @RequestBody SurveyFormRequest survey, HttpServletRequest request, HttpServletResponse response) {
        surveyAgregatorRestClientImpl.sendSurvey(survey, request, response);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/change_password")
    public String changeUserPassword(Model model) {
        return "change_password";
    }

    @PutMapping("/user/change_password")
    public ResponseEntity<?> sendNewPassword(Model model, @RequestBody UserUpdateRequest pass, HttpServletRequest request, HttpServletResponse response) {
        String resp = surveyAgregatorRestClientImpl.sendNewPassword(pass, request, response);
        if (resp == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/access_token")
    public ResponseEntity<?> sendAccessToken(Model model, HttpServletRequest request, HttpServletResponse response) {
        String resp = surveyAgregatorRestClientImpl.sendAccessToken(request, response);
        if (resp == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> sendRefreshToken(Model model, HttpServletRequest request, HttpServletResponse response) {
        AuthenticationResponseDto resp = surveyAgregatorRestClientImpl.getNewTokens(request, response);
        if (resp == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(resp);
        }
    }
}