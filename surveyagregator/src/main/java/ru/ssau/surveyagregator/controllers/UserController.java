package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.requests.UserFormRequest;
import ru.ssau.surveyagregator.requests.UserLoginRequest;
import ru.ssau.surveyagregator.requests.UserUpdateRequest;
import ru.ssau.surveyagregator.responses.UserProfileResponse;
import ru.ssau.surveyagregator.service.SurveyService;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class UserController {
    private final SurveyService surveyService;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/surveys")
    ResponseEntity<?> createSurvey(@RequestBody SurveyFormRequest survey) {
        List<User> users = userServiceImpl.findAllById(survey.getAdminId());
        surveyService.createSurvey(survey, users);
        return ResponseEntity.ok("Survey was created");
    }

    @PostMapping("/register")
    ResponseEntity<?> registerAdmin(@RequestBody UserFormRequest admin) {
        userServiceImpl.registerAdmin(admin);
        return ResponseEntity.ok("Registration success");
    }

    @PostMapping("/login")
    ResponseEntity<?> loginAdmin(@RequestBody UserLoginRequest admin) {
        return ResponseEntity.ok("Login success");
    }

    @GetMapping("/profile")
    ResponseEntity<?> profileAdmin(@RequestParam UUID id) {
        User user = userServiceImpl.findById(id);
        return ResponseEntity.ok(new UserProfileResponse(user.getEmail(), user.getUsername()));
    }

    @GetMapping("/surveys")
    ResponseEntity<?> allAdminSurveys(@RequestParam UUID id) {
        return ResponseEntity.ok(surveyService.findSurveys(id));
    }

    @GetMapping("/surveys/{id}")
    ResponseEntity<?> adminSurvey(@PathVariable UUID id) {
        return ResponseEntity.ok(surveyService.findAdminSurvey(id));
    }

    @PutMapping("/profile")
    ResponseEntity<?> updateSurvey(@RequestParam UUID id, @RequestBody UserUpdateRequest request) {
        User user = userServiceImpl.findById(id);
        userServiceImpl.update(request, user);
        return ResponseEntity.ok("Profile updated");
    }
}
