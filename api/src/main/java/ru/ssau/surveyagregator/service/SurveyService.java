package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.repository.SurveyRepository;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.responses.SurveyResponse;
import ru.ssau.surveyagregator.responses.UserProfileResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final AnswerService answerService;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, AnswerService answerService) {
        this.surveyRepository = surveyRepository;
        this.answerService = answerService;
    }

    @Transactional
    public boolean createSurvey(SurveyFormRequest survey, List<User> users) {
        Survey newSurvey = Survey
                .builder()
                .surveyDescription(survey.getDescription())
                .surveyTitle(survey.getTitle())
                .build();
        for (User user : users) {
            newSurvey.addUser(user);
        }
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

    @Transactional
    public List<UserProfileResponse.SurveysInfo> findSurveys(UUID id) {
        List<Survey> surveys = surveyRepository.findSurveysByUserId(id);
        List<UserProfileResponse.SurveysInfo> surveysInfos = new ArrayList<>();
        for (Survey s : surveys) {
            surveysInfos.add(new UserProfileResponse.SurveysInfo(s.getId(), s.getSurveyTitle(), s.getSurveyDescription()));
        }
        return surveysInfos;
    }

    @Transactional
    public List<UUID> findSurveysIds(UUID id) {
        List<Survey> surveys = surveyRepository.findSurveysByUserId(id);
        List<UUID> ids = new ArrayList<>();
        for (Survey s : surveys) {
            ids.add(s.getId());
        }
        return ids;
    }

    @Transactional
    public ResponseEntity<?> findSurvey(UUID id) {
        if (!surveyRepository.existsById(id))
            return ResponseEntity.notFound().build();
        Survey survey = surveyRepository.findById(id).get();
        SurveyResponse response = new SurveyResponse();
        response.setTitle(survey.getSurveyTitle());
        response.setDescription(survey.getSurveyDescription());
        List<SurveyResponse.QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            List<SurveyResponse.AnswerResponse> answerResponses = new ArrayList<>();
            SurveyResponse.QuestionResponse questionResponse = new SurveyResponse.QuestionResponse();
            questionResponse.setQuestionText(question.getQuestionText());
            for (Answer answer : question.getAnswers()) {
                SurveyResponse.AnswerResponse answerResponse = new SurveyResponse.AnswerResponse(answer.getAnswerText(), answer.getId());
                answerResponses.add(answerResponse);
            }
            questionResponse.setAnswers(answerResponses);
            questionResponses.add(questionResponse);
        }
        response.setQuestions(questionResponses);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<?> saveAnswer(AnswerRequest request) {
        answerService.saveAnswer(request.getAnswerIds());
        return ResponseEntity.ok("Ответ сохранён");
    }

    public void clear() {
        surveyRepository.deleteAll();
    }
}
