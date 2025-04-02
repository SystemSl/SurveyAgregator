package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.model.Admin;
import ru.ssau.surveyagregator.model.Answer;
import ru.ssau.surveyagregator.model.Question;
import ru.ssau.surveyagregator.model.Survey;
import ru.ssau.surveyagregator.service.AdminService;
import ru.ssau.surveyagregator.service.AnswerService;
import ru.ssau.surveyagregator.service.QuestionService;
import ru.ssau.surveyagregator.service.SurveyService;

@SpringBootTest
public class GlobalTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Test
    public void testAllEntities() {
        adminService.clear();
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
        Admin admin = Admin.builder().name(name).password(password).email(email).build();
        Q1.addAnswer(A11);
        Q1.addAnswer(A12);
        Q2.addAnswer(A21);
        Q2.addAnswer(A22);
        survey.addQuestion(Q1);
        survey.addQuestion(Q2);
        survey.addAdmin(admin);
        adminService.registerAdmin(admin);
        surveyService.createSurvey(survey);
    }


    @Test
    public void delete() {
        adminService.clear();
        surveyService.clear();
    }
}
