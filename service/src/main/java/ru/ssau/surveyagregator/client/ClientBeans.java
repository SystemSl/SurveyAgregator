package ru.ssau.surveyagregator.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public SurveyAgregatorRestClientImpl surveyAgregatorRestClient() {
        return new SurveyAgregatorRestClientImpl(RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build());
    }
}
