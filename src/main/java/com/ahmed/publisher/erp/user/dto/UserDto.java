package com.ahmed.publisher.erp.user.dto;
import java.util.UUID;

import com.ahmed.publisher.erp.user.entity.Role;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Role role
) {}

