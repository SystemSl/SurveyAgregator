package ru.ssau.surveyagregator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyResponse {
    private List<QuestionResponse> questions;
    private String title;
    private String description;

    @Data
    public static class QuestionResponse {
        private String questionText;
        private List<AnswerResponse> answers;
    }
    @AllArgsConstructor
    @Data
    public static class AnswerResponse {
        private String answer;
        private UUID id;
    }
}
