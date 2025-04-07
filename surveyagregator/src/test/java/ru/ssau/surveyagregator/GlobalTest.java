package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.service.AnswerService;
import ru.ssau.surveyagregator.service.SurveyService;
import ru.ssau.surveyagregator.service.UserServiceImpl;

@SpringBootTest
public class GlobalTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private SurveyService surveyService;


    @Autowired
    private AnswerService answerService;

    @Test
    public void testAllEntities() {
        userServiceImpl.clear();
        surveyService.clear();
    }
}
