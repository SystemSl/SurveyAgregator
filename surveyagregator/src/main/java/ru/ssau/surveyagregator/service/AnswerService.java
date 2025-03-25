package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.repository.AnswerRepository;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Transactional
    public boolean createAnswer(String text) {
        Answer newAnswer = Answer.builder().answerText(text).build();
        answerRepository.save(newAnswer);
        return true;
    }

    @Transactional
    public boolean createAnswer(Answer newAnswer) {
        answerRepository.save(newAnswer);
        return true;
    }

    @Transactional
    public boolean saveAnswer(List<Integer> answersId) {
        List<Answer> answers = answerRepository.findAllById(answersId);
        for (Answer answer : answers) {
            answer.setAnswerQuantity(answer.getAnswerQuantity() + 1);
        }
        return true;
    }

    public void clear() {
        answerRepository.deleteAll();
    }
}

