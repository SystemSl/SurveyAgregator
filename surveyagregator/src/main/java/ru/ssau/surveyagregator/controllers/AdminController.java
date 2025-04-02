package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.model.Admin;
import ru.ssau.surveyagregator.requests.AdminFormRequest;
import ru.ssau.surveyagregator.requests.AdminLoginRequest;
import ru.ssau.surveyagregator.requests.AdminUpdateRequest;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.responses.AdminProfileResponse;
import ru.ssau.surveyagregator.service.AdminService;
import ru.ssau.surveyagregator.service.SurveyService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final SurveyService surveyService;
    private final AdminService adminService;

    @PostMapping("/surveys")
    ResponseEntity<?> createSurvey(@RequestBody SurveyFormRequest survey) {
        List<Admin> admins = adminService.findAllById(survey.getAdminId());
        surveyService.createSurvey(survey, admins);
        return ResponseEntity.ok("Survey was created");
    }

    @PostMapping("/register")
    ResponseEntity<?> registerAdmin(@RequestBody AdminFormRequest admin) {
        adminService.registerAdmin(admin);
        return ResponseEntity.ok("Registration success");
    }

    @PostMapping("/login")
    ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest admin) {
        return ResponseEntity.ok("Login success");
    }

    @GetMapping("/profile")
    ResponseEntity<?> profileAdmin(@RequestParam UUID id) {
        Admin admin = adminService.findById(id);
        return ResponseEntity.ok(new AdminProfileResponse(admin.getEmail(), admin.getName()));
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
    ResponseEntity<?> updateSurvey(@RequestParam UUID id, @RequestBody AdminUpdateRequest request) {
        Admin admin = adminService.findById(id);
        adminService.update(request, admin);
        return ResponseEntity.ok("Profile updated");
    }
}
