package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.surveyagregator.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
