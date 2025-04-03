package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;
    private String password;
}
