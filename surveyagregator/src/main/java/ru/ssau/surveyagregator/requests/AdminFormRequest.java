package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class AdminFormRequest {
    private String name;
    private String email;
    private String password;
}
