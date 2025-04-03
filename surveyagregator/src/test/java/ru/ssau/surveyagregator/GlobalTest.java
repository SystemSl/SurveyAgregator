package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.service.AnswerService;
import ru.ssau.surveyagregator.service.QuestionService;
import ru.ssau.surveyagregator.service.SurveyService;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import java.util.UUID;

@SpringBootTest
public class GlobalTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Test
    public void testAllEntities() {
        userServiceImpl.clear();
        surveyService.clear();
        String name = "admin";
        String email = "admin@example.com";
        String password = "password";
        String title = "Survey 1";
        String description = "Test survey";
        Question Q1 = Question.builder().questionText("First question").build();
        Answer A11 = Answer.builder().answerText("Yes").build();
        Answer A12 = Answer.builder().answerText("No").build();
        Question Q2 = Question.builder().questionText("Second question").build();
        Answer A21 = Answer.builder().answerText("Yes").build();
        Answer A22 = Answer.builder().answerText("No").build();
        Survey survey = Survey.builder().surveyDescription(description).surveyTitle(title).build();
        User user = User.builder().username(name).password(password).email(email).build();
        Q1.addAnswer(A11);
        Q1.addAnswer(A12);
        Q2.addAnswer(A21);
        Q2.addAnswer(A22);
        survey.addQuestion(Q1);
        survey.addQuestion(Q2);
        survey.addUser(user);
        userServiceImpl.registerAdmin(user);
        surveyService.createSurvey(survey);
    }


    @Test
    public void delete() {
        UUID a = UUID.fromString("9c64733b-e0d5-4490-aa12-ac8c829b6db9");
    }
}
