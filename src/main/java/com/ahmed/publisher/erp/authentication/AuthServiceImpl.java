package com.ahmed.publisher.erp.authentication;

import com.ahmed.publisher.erp.user.User;
import com.ahmed.publisher.erp.user.UserService;
import com.ahmed.publisher.erp.user.dto.AuthResponse;
import com.ahmed.publisher.erp.user.dto.LoginRequest;
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
                .orElseThrow(() -> new RuntimeException("Token creation not supported for this strategy"));
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByEmailAndPassword(request.email(), request.password());
        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException("Token creation not supported for this strategy"));
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        if (!tokenService.validate(refreshToken)) {
            throw new RuntimeException("Invalid token");
        }
        UUID userId = tokenService.extractUserId(refreshToken)
                .orElseThrow(() -> new RuntimeException("Cannot extract user ID from token"));
        User user = userService.findById(userId);

        String token = tokenService.createToken(user)
                .orElseThrow(() -> new RuntimeException("Token creation not supported for this strategy"));
        return new AuthResponse(token);
    }

    /* For future enablement of AuthType selection from user/frontend's side
    public AuthResponse login(LoginRequest request) {
    TokenService tokenService;
    User user;

    switch(request.getAuthMethod()) {
        case "EMAIL":
            user = userService.findByEmailAndPassword(request.email(), request.password());
            tokenService = tokenServices.get("jwtTokenService");
            break;
        case "GOOGLE":
            user = oauthService.verifyGoogleToken(request.getOauthToken());
            tokenService = tokenServices.get("oauthTokenService");
            break;
        case "GITHUB":
            user = oauthService.verifyGithubToken(request.getOauthToken());
            tokenService = tokenServices.get("oauthTokenService");
            break;
        default:
            throw new RuntimeException("Unknown auth method");
    }

    String token = tokenService.createToken(user)
            .orElseThrow(() -> new RuntimeException("Token creation not supported"));

    return new AuthResponse(token);
}

     */
}
