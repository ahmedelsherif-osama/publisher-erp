package com.ahmed.publisher.erp.authentication.service;

import com.ahmed.publisher.erp.user.entity.User;
import com.ahmed.publisher.erp.user.service.UserService;
import com.ahmed.publisher.erp.authentication.dto.AuthResponse;
import com.ahmed.publisher.erp.authentication.dto.LoginRequest;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserService userService;
    private final TokenService tokenService;

    public AuthServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse register(UserRegistrationRequest request) {
        log.info("Registering new user, email={}", request.email());
        User user = userService.create(request);
        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException(
                        "Token creation not supported for this strategy: " + tokenService.getType()));
        log.info("User registered successfully, userId={}, tokenType={}", user.getId(), tokenService.getType());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Authenticating user, email={}", request.email());
        User user = userService.findByEmailAndPassword(request.email(), request.password());
        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException(
                        "Token creation not supported for this strategy: " + tokenService.getType()));
        log.info("User authenticated successfully, userId={}, tokenType={}", user.getId(), tokenService.getType());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        log.info("Refreshing authentication token");
        // validate token first
        if (!tokenService.validate(refreshToken)) {
            log.warn("Invalid refresh token received");
            throw new RuntimeException("Invalid token");
        }

        // extract user ID (may fail for OAuth, ephemeral tokens, etc.)
        UUID userId = tokenService.extractUserId(refreshToken)
                .orElseThrow(() -> new RuntimeException(
                        "Cannot extract user ID from token: " + tokenService.getType()));
        log.debug("Refresh token validated, userId={}", userId);

        User user = userService.findById(userId);

        // attempt to create a new token
        return tokenService.createToken(user)
                .map(token -> {
                    log.info("Token refreshed successfully, userId={}, tokenType={}", userId, tokenService.getType());
                    return new AuthResponse(token);
                }) // if token is supported, return it
                .orElseGet(() -> {
                    log.warn("Token refresh not supported for strategy={}", tokenService.getType());
                    return new AuthResponse( "Token refresh not supported for this strategy: " + tokenService.getType());
                        }

                );
    }
}
