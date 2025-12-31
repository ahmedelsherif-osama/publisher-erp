package com.ahmed.publisher.erp.user.dto;
import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName
) {
}
