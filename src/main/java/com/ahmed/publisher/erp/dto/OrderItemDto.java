package com.ahmed.publisher.erp.dto;

import java.util.UUID;

public record OrderItemDto(
        UUID id,
        UUID productId,
        String productName,
        int quantity
) {}
