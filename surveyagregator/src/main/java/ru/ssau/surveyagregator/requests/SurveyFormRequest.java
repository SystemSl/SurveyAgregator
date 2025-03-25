package ru.ssau.surveyagregator.requests;

import lombok.Data;

import java.util.List;

@Data
public class SurveyFormRequest {
    private List<Integer> adminId;
    private List<QuestionRequest> questions;
    private String title;
    private String description;

    @Data
    public static class QuestionRequest {
        private String questionText;
        private List<String> answers;
    }
}
