package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.surveyagregator.model.Question;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

}
