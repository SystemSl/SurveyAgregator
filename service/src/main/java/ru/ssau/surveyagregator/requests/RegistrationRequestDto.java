package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class RegistrationRequestDto {

    private String username;
    private String email;
    private String password;
}
