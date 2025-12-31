package com.ahmed.publisher.erp.user.dto;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
){}
