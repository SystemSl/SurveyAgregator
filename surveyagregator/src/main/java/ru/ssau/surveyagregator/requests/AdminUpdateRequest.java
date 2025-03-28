package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class AdminUpdateRequest {
    private String name;
    private String email;
}
