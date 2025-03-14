package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.repository.AnswerRepository;

@Service
public class AnswerService {
    private final AnswerRepository AnswerRepository;

    @Autowired
    public AnswerService(AnswerRepository AnswerRepository) {
        this.AnswerRepository = AnswerRepository;
    }

    @Transactional
    public boolean createAnswer(String text, Integer quantity) {
        Answer newAnswer = new Answer(text, quantity);
        AnswerRepository.save(newAnswer);
        return true;
    }

    @Transactional
    public boolean createAnswer(Answer newAnswer) {
        AnswerRepository.save(newAnswer);
        return true;
    }
}

