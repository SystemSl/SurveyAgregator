package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.repository.AnswerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Transactional
    public boolean saveAnswer(List<String> answersId) {
        List<UUID> ids = new ArrayList<>();
        for (String id : answersId) {
            ids.add(UUID.fromString(id));
        }
        List<Answer> answers = answerRepository.findAllById(ids);
        for (Answer answer : answers) {
            answer.setAnswerQuantity(answer.getAnswerQuantity().add(BigDecimal.ONE));
        }
        return true;
    }
}

