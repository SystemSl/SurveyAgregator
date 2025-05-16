package ru.ssau.surveyagregator.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.surveyagregator.requests.LoginRequestDto;
import ru.ssau.surveyagregator.requests.RegistrationRequestDto;
import ru.ssau.surveyagregator.responses.AuthenticationResponseDto;
import ru.ssau.surveyagregator.service.AuthenticationService;
import ru.ssau.surveyagregator.service.UserServiceImpl;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserServiceImpl userServiceImpl;

    public AuthenticationController(AuthenticationService authenticationService, UserServiceImpl userServiceImpl) {
        this.authenticationService = authenticationService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(
            @RequestBody RegistrationRequestDto registrationDto
    ) {

        if (userServiceImpl.existsByUsername(registrationDto.getUsername())) {
            return ResponseEntity.badRequest().body("Имя пользователя уже занято");
        }

        if (userServiceImpl.existsByEmail(registrationDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email уже занят");
        }

        authenticationService.register(registrationDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authenticationService.refreshToken(request, response);
    }

    @PostMapping("/access_token")
    public ResponseEntity<?> accessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("OK");
    }
}
