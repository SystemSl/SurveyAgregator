package ru.ssau.surveyagregator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class AdminSurveysResponse {
    private List<String> titles;
    private List<String> descriptions;
    private List<Integer> ids;
}
