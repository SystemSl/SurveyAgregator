package ru.ssau.surveyagregator.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AnswerRequest {
    private List<UUID> answersId;
}
