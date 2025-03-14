package ru.ssau.surveyagregator;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.surveyagregator.model.Admin;
import ru.ssau.surveyagregator.service.AdminService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminTests {

    @Autowired
    private AdminService adminService;

    @Test
    public void testRegisterUser() {
        String name = "admin";
        String email = "admin@example.com";
        String password = "password";
        //Admin admin = new Admin(name, email, password);
        //System.out.printf();
        assertEquals(true, adminService.registerAdmin(name, email, password));
    }
}
