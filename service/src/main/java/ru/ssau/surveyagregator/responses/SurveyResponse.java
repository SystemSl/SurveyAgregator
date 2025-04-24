package ru.ssau.surveyagregator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyResponse {
    public List<QuestionResponse> questions;
    public String title;
    public String description;

    @Data
    public static class QuestionResponse {
        public String questionText;
        public List<AnswerResponse> answers;
    }

    @AllArgsConstructor
    @Data
    public static class AnswerResponse {
        public String answer;
        public UUID id;
    }
}
