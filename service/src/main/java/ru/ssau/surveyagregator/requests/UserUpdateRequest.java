package ru.ssau.surveyagregator.requests;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String oldPassword;
    private String newPassword;
}
