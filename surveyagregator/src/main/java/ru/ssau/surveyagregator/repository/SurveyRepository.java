package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.surveyagregator.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

}
