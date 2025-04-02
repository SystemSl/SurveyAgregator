package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.service.SurveyService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{id}")
    ResponseEntity<?> findSurvey(@PathVariable UUID id) {
        return ResponseEntity.ok(surveyService.findSurvey(id));
    }

    @PostMapping("/{id}")
    ResponseEntity<?> sendAnswer(@PathVariable UUID id, @RequestBody AnswerRequest answer) {
        System.out.println("avo");
        surveyService.saveAnswer(id, answer);
        return ResponseEntity.ok("Answers saved");
    }
}
