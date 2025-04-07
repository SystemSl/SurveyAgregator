package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class UserFormRequest {
    private String name;
    private String email;
    private String password;
}
