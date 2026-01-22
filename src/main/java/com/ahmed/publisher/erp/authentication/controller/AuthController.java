package com.ahmed.publisher.erp.authentication.controller;

import com.ahmed.publisher.erp.authentication.service.AuthService;
import com.ahmed.publisher.erp.authentication.dto.RefreshTokenRequest;
import com.ahmed.publisher.erp.authentication.dto.AuthResponse;
import com.ahmed.publisher.erp.authentication.dto.LoginRequest;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        log.info("Auth login request received, email={}", request.email());
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody @Valid RefreshTokenRequest request) {
        log.info("Auth token refresh request received");
        return authService.refresh(request.refreshToken());
    }
}
