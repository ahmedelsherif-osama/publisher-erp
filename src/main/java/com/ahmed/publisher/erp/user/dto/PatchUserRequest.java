package com.ahmed.publisher.erp.user.dto;
import com.ahmed.publisher.erp.user.entity.Role;
import jakarta.validation.constraints.Email;

import java.util.List;

public record PatchUserRequest(
        String firstName,
        String lastName,

        @Email
        String email,

        Role role,

        List<AddressDto> addresses


) {}

