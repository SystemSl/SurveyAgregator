package ru.ssau.surveyagregator.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.requests.LoginRequestDto;
import ru.ssau.surveyagregator.requests.RegistrationRequestDto;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.responses.SurveyResponse;
import ru.ssau.surveyagregator.responses.UserProfileResponse;
import ru.ssau.surveyagregator.responses.UserSurveyResponse;

import java.util.UUID;

public interface SurveyAgregatorRestClient {
    AuthenticationResponseDto getTokens(LoginRequestDto request);

    SurveyResponse getSurvey(UUID id);

    UserProfileResponse getUserInfo(HttpServletRequest request, HttpServletResponse response);

    UserSurveyResponse getUserSurvey();

    String sendAnswer(AnswerRequest answer);

    String logoutTokens(HttpServletRequest request, HttpServletResponse response);

    String sendUserRegistration(RegistrationRequestDto request);
}
