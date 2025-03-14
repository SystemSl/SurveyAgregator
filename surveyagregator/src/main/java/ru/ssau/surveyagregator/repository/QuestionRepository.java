package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.surveyagregator.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
