package ru.ssau.surveyagregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.service.SurveyService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping()
    ResponseEntity<?> findSurvey(@RequestParam UUID id) {
        return surveyService.findSurvey(id);
    }

    @PostMapping()
    ResponseEntity<?> sendAnswer(@RequestBody AnswerRequest answer) {
        return surveyService.saveAnswer(answer);
    }
}
