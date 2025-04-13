package ru.ssau.surveyagregator.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.requests.UserUpdateRequest;
import ru.ssau.surveyagregator.service.AuthenticationService;
import ru.ssau.surveyagregator.service.SurveyService;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationService authenticationService;
    private final SurveyService surveyService;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    ResponseEntity<?> createSurvey(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody SurveyFormRequest survey) {
        return authenticationService.createSurvey(request, response, survey);
    }

    @GetMapping("/profile")
    ResponseEntity<?> profileUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authenticationService.userInfo(request, response);
    }

    @GetMapping("/surveys")
    ResponseEntity<?> adminSurvey(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam UUID id
    ) {
        return authenticationService.findUserSurvey(request, response, id);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updatePassword(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody UserUpdateRequest uureq
    ) {
        return authenticationService.updatePassword(request, response, uureq);
    }
}
