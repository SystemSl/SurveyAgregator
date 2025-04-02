package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.surveyagregator.model.Answer;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

}
