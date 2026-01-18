package com.ahmed.publisher.erp.user.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import com.ahmed.publisher.erp.user.entity.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotNull
        Role role,


        @NotNull @NotEmpty @Valid List<AddressDto> addresses
) {}

