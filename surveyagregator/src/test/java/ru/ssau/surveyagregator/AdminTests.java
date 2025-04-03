package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminTests {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void testRegisterUser() {
        String name = "admin";
        String email = "admin@example.com";
        String password = "password";
        assertEquals(true, userServiceImpl.registerAdmin(name, email, password));
    }
}
