package ru.ssau.surveyagregator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public SurveyAgregatorRestClientImpl surveyAgregatorRestClient(
            @Value("${surveyagregator.api}") String apiUri
    ) {
        return new SurveyAgregatorRestClientImpl(RestClient.builder()
                .baseUrl(apiUri)
                .build());
    }
}
