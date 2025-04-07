package ru.ssau.surveyagregator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class UserProfileResponse {
    private String email;
    private String username;
    private String id;
    private UserSurveysResponse surveys;
}
