package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.repository.SurveyRepository;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public boolean createSurvey(String title, String description) {
        Survey newSurvey = Survey.builder().surveyDescription(description).surveyTitle(title).build();
        surveyRepository.save(newSurvey);
        return true;
    }

    @Transactional
    public boolean createSurvey(Survey newSurvey) {
        surveyRepository.save(newSurvey);
        return true;
    }

    @Transactional
    public boolean createSurvey(SurveyFormRequest survey) {
        Survey newSurvey = Survey
                .builder()
                .surveyDescription(survey.getDescription())
                .surveyTitle(survey.getTitle())
                .build();
        for (SurveyFormRequest.QuestionRequest formQuestion : survey.getQuestions()) {
            Question question = Question.builder().questionText(formQuestion.getQuestionText()).build();
            for (String formAnswer : formQuestion.getAnswers()) {
                Answer answer = Answer.builder().answerText(formAnswer).build();
                question.addAnswer(answer);
            }
            newSurvey.addQuestion(question);
        }
        surveyRepository.save(newSurvey);
        return true;
    }

    public void clear() {
        surveyRepository.deleteAll();
    }
}
