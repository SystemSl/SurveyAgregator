package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.repository.QuestionRepository;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public boolean createQuestion(String text) {
        Question newQuestion = new Question(text);
        questionRepository.save(newQuestion);
        return true;
    }

    @Transactional
    public boolean createQuestion(Question newQuestion) {
        questionRepository.save(newQuestion);
        return true;
    }
}

