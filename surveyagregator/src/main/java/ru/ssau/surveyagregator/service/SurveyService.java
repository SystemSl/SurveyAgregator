package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.repository.SurveyRepository;
import ru.ssau.surveyagregator.requests.AnswerRequest;
import ru.ssau.surveyagregator.requests.SurveyFormRequest;
import ru.ssau.surveyagregator.responses.AdminSurveyResponse;
import ru.ssau.surveyagregator.responses.AdminSurveysResponse;
import ru.ssau.surveyagregator.responses.SurveyResponse;

import java.util.ArrayList;
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
    public AdminSurveysResponse findSurveys(UUID id) {
        List<Survey> surveys = surveyRepository.findSurveysByAdminId(id);
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (Survey s : surveys) {
            titles.add(s.getSurveyTitle());
            descriptions.add(s.getSurveyDescription());
            ids.add(s.getId());
        }
        return new AdminSurveysResponse(titles, descriptions, ids);
    }

    @Transactional
    public AdminSurveyResponse findAdminSurvey(UUID id) {
        Survey survey = surveyRepository.findById(id).get();
        AdminSurveyResponse response = new AdminSurveyResponse();
        response.setTitle(survey.getSurveyTitle());
        response.setDescription(survey.getSurveyDescription());
        List<AdminSurveyResponse.QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            List<AdminSurveyResponse.AnswerResponse> answerResponses = new ArrayList<>();
            AdminSurveyResponse.QuestionResponse questionResponse = new AdminSurveyResponse.QuestionResponse();
            questionResponse.setQuestionText(question.getQuestionText());
            for (Answer answer : question.getAnswers()) {
                AdminSurveyResponse.AnswerResponse answerResponse = new AdminSurveyResponse.AnswerResponse(answer.getAnswerText(), answer.getAnswerQuantity());
                answerResponses.add(answerResponse);
            }
            questionResponse.setAnswers(answerResponses);
            questionResponses.add(questionResponse);
        }
        response.setQuestions(questionResponses);
        return response;
    }

    @Transactional
    public SurveyResponse findSurvey(UUID id) {
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
        return response;
    }

    @Transactional
    public boolean saveAnswer(UUID id, AnswerRequest request) {
        Survey survey = surveyRepository.findById(id).get();
        answerService.saveAnswer(request.getAnswersId());
        return true;
    }

    public void clear() {
        surveyRepository.deleteAll();
    }
}
