package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.service.AdminService;
import ru.ssau.surveyagregator.service.SurveyService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final SurveyService service;
    private final AdminService adminService;

    @PostMapping("/surveys")
    ResponseEntity<?> createSurvey(@RequestBody SurveyFormRequest survey) {
        service.createSurvey(survey);
        return ResponseEntity.ok("Всё хорошо");
    }
}
