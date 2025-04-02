package ru.ssau.surveyagregator.requests;

import lombok.Data;

import java.util.List;

@Data
public class AnswerRequest {
    private List<String> answersId;
}
