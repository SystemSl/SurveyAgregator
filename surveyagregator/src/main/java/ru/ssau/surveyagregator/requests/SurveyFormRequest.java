package ru.ssau.surveyagregator.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyFormRequest {
    private List<UUID> adminId;
    private List<QuestionRequest> questions;
    private String title;
    private String description;

    @Data
    public static class QuestionRequest {
        private String questionText;
        private List<String> answers;
    }
}
