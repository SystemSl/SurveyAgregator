package ru.ssau.surveyagregator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class UserSurveysResponse {
    private List<String> titles;
    private List<String> descriptions;
    private List<UUID> ids;
}
