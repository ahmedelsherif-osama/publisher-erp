package com.ahmed.publisher.erp.authentication;

import com.ahmed.publisher.erp.user.dto.AuthResponse;
import com.ahmed.publisher.erp.user.dto.LoginRequest;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;


public interface AuthService {
    AuthResponse register(UserRegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(String refreshToken);
}

