package com.ahmed.publisher.erp.authentication.service;

import com.ahmed.publisher.erp.user.entity.User;
import com.ahmed.publisher.erp.user.service.UserService;
import com.ahmed.publisher.erp.authentication.dto.AuthResponse;
import com.ahmed.publisher.erp.authentication.dto.LoginRequest;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse register(UserRegistrationRequest request) {
        User user = userService.create(request);
        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException(
                        "Token creation not supported for this strategy: " + tokenService.getType()));
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByEmailAndPassword(request.email(), request.password());
        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException(
                        "Token creation not supported for this strategy: " + tokenService.getType()));
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        // validate token first
        if (!tokenService.validate(refreshToken)) {
            throw new RuntimeException("Invalid token");
        }

        // extract user ID (may fail for OAuth, ephemeral tokens, etc.)
        UUID userId = tokenService.extractUserId(refreshToken)
                .orElseThrow(() -> new RuntimeException(
                        "Cannot extract user ID from token: " + tokenService.getType()));

        User user = userService.findById(userId);

        // attempt to create a new token
        return tokenService.createToken(user)
                .map(AuthResponse::new) // if token is supported, return it
                .orElseGet(() -> new AuthResponse("Token refresh not supported for this strategy: " + tokenService.getType()));
    }
}
