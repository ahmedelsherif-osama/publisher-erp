package com.ahmed.publisher.erp.user.dto;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotNull String firstName,
        @NotNull String lastName
        ) {}
