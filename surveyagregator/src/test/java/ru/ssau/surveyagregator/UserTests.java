package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void testRegisterUser() {
    }
}
