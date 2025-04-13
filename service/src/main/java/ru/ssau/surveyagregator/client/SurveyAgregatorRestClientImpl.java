package ru.ssau.surveyagregator.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.requests.LoginRequestDto;
import ru.ssau.surveyagregator.requests.RegistrationRequestDto;
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
                System.out.println("Survey not found with ID: " + id);
                return null; // Или возвращайте специальное значение, сигнализирующее об отсутствии ресурса
            } else {
                System.err.println("An unexpected error occurred: " + e.getMessage());
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
    public UserSurveyResponse getUserSurvey() {
        return null;
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
        return this.restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/registration")
                        .build())
                .header("Content-Type", "application/json")
                .body(request)
                .retrieve()
                .body(String.class);
    }
}
