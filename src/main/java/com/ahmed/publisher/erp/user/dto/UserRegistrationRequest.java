package com.ahmed.publisher.erp.user.dto;

import com.ahmed.publisher.erp.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        @Email(message = "Invalid email format")
        @UniqueEmail(message = "Email already exists")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
){}
