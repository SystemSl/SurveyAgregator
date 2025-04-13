package ru.ssau.surveyagregator.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ssau.surveyagregator.client.SurveyAgregatorRestClientImpl;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.requests.LoginRequestDto;
import ru.ssau.surveyagregator.requests.RegistrationRequestDto;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.responses.SurveyResponse;
import ru.ssau.surveyagregator.responses.UserProfileResponse;

import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private SurveyAgregatorRestClientImpl surveyAgregatorRestClientImpl;

    @GetMapping("/")
    public String main(Model model) {
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registrationSuccess(Model model, @RequestBody RegistrationRequestDto request) {
        String response = surveyAgregatorRestClientImpl.sendUserRegistration(request);
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
            return "not_found";
    }

    @PostMapping("/survey")
    public String submitSurvey(Model model, @RequestBody AnswerRequest answer) {
        String response = surveyAgregatorRestClientImpl.sendAnswer(answer);
        return "registration";
    }

}
