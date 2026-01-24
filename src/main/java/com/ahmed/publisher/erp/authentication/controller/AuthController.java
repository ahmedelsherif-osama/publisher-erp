package com.ahmed.publisher.erp.authentication.controller;

import com.ahmed.publisher.erp.authentication.service.AuthService;
import com.ahmed.publisher.erp.authentication.dto.RefreshTokenRequest;
import com.ahmed.publisher.erp.authentication.dto.AuthResponse;
import com.ahmed.publisher.erp.authentication.dto.LoginRequest;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid UserRegistrationRequest request) {
        log.info("Auth register request received, email={}", request.email());
        log.debug("UserRegistrationRequest payload: {}", request);

        AuthResponse response = authService.register(request);

        log.info("Registration successful for email={}", request.email());
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        log.info("Auth login request received, email={}", request.email());
        log.debug("LoginRequest payload: {}", request);

        AuthResponse response = authService.login(request);

        log.info("Login successful for email={}", request.email());
        return response;
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody @Valid RefreshTokenRequest request) {
        log.info("Auth token refresh request received");
        log.debug("RefreshTokenRequest payload: {}", request);

        AuthResponse response = authService.refresh(request.refreshToken());

        log.info("Token refreshed successfully");
        return response;
    }
}
