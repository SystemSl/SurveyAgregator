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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String name = "admin";
        String email = "admin@example.com";
        String password = "password";
        String title = "Survey 1";
        String description = "Test survey";
        Set<Question> questions = new HashSet<>();
        Set<Answer> answers1 = new HashSet<>();
        Set<Answer> answers2 = new HashSet<>();
        Question Q1 = new Question("First question");
        Answer A11 = new Answer("Yes",0);
        Answer A12 = new Answer("No",0);
        Question Q2 = new Question("Second question");
        Answer A21 = new Answer("Yes",0);
        Answer A22 = new Answer("No",0);
        answers1.add(A11);
        answers1.add(A12);
        answers2.add(A21);
        answers2.add(A22);
        Q1.setAnswers(answers1);
        Q2.setAnswers(answers2);
        questions.add(Q1);
        questions.add(Q2);

        Survey survey = new Survey(title, description);
        survey.setQuestions(questions);
        Set<Admin> admins = new HashSet<>();
        Admin admin = new Admin(name, email, password);
        adminService.registerAdmin(admin);
        admins.add(admin);
        survey.setAdmins(admins);
    }
}
