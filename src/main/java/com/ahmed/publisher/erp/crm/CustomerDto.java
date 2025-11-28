package com.ahmed.publisher.erp.crm;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String email
) {
}
