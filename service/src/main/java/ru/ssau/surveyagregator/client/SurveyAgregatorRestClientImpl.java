package ru.ssau.surveyagregator.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.ssau.surveyagregator.requests.*;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.responses.SurveyResponse;
import ru.ssau.surveyagregator.responses.UserProfileResponse;
import ru.ssau.surveyagregator.responses.UserSurveyResponse;

import java.util.UUID;

@RequiredArgsConstructor
public class SurveyAgregatorRestClientImpl implements SurveyAgregatorRestClient {

    private final RestClient restClient;

    @Override
    public AuthenticationResponseDto getTokens(LoginRequestDto request) {
        return this.restClient
                .post()
                .uri("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(AuthenticationResponseDto.class);
    }

    @Override
    public String logoutTokens(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return this.restClient
                .post()
                .uri("/api/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .body(String.class);
    }

    @Override
    public SurveyResponse getSurvey(UUID id) {
        try {
            return this.restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/survey")
                            .queryParam("id", id)
                            .build())
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .body(SurveyResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return null; // Или возвращайте специальное значение, сигнализирующее об отсутствии ресурса
            } else {
                throw e; // Перенаправляем исключение дальше по цепочке
            }
        }
    }

    @Override
    public UserProfileResponse getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return this.restClient
                .get()
                .uri("/api/user/profile")
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .body(UserProfileResponse.class);
    }

    @Override
    public UserSurveyResponse getUserSurvey(UUID id, HttpServletRequest request, HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return this.restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/user/survey")
                        .queryParam("id", id)
                        .build())
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .body(UserSurveyResponse.class);
    }

    @Override
    public String sendAnswer(AnswerRequest answer) {
        return this.restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/survey")
                        .build())
                .header("Content-Type", "application/json")
                .body(answer)
                .retrieve()
                .body(String.class);
    }

    @Override
    public String sendUserRegistration(RegistrationRequestDto request) {
        try {
            return this.restClient
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/registration")
                            .build())
                    .header("Content-Type", "application/json")
                    .body(request)
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                return switch (e.getResponseBodyAsString()) {
                    case "Имя пользователя уже занято" -> "username";
                    case "Email уже занят" -> "email";
                    default -> null;
                };
            } else {
                throw e;
            }
        }
    }

    @Override
    public String sendSurvey(SurveyFormRequest survey, HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return this.restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/user/create")
                        .build())
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeader)
                .body(survey)
                .retrieve()
                .body(String.class);
    }

    @Override
    public String sendNewPassword(UserUpdateRequest pass, HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return this.restClient
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/user/profile")
                            .build())
                    .header("Content-Type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .body(pass)
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                return null; // Или возвращайте специальное значение, сигнализирующее об отсутствии ресурса
            } else {
                throw e; // Перенаправляем исключение дальше по цепочке
            }
        }
    }

    @Override
    public String sendAccessToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return this.restClient
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/access_token")
                            .build())
                    .header("Content-Type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AuthenticationResponseDto getNewTokens(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return this.restClient
                    .post()
                    .uri("/api/refresh_token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Content-Type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .retrieve()
                    .body(AuthenticationResponseDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
