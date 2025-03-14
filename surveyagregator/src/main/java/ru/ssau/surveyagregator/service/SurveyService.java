package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.repository.SurveyRepository;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public boolean createSurvey(String title, String description) {
        Survey newSurvey = new Survey(title, description);
        surveyRepository.save(newSurvey);
        return true;
    }
}
