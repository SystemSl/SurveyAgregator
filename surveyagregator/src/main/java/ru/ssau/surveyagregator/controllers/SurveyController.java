package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.requests.AdminUpdateRequest;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.service.SurveyService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{id}")
    ResponseEntity<?> findSurvey(@PathVariable Integer id) {
        return ResponseEntity.ok(surveyService.findSurvey(id));
    }

    @PostMapping("/{id}")
    ResponseEntity<?> sendAnswer(@PathVariable Integer id, @RequestBody AnswerRequest answer) {
        surveyService.saveAnswer(id, answer);
        return ResponseEntity.ok("Answers saved");
    }
}
