package com.ahmed.publisher.erp.order.dto;

import java.util.UUID;

public record OrderItemDto(
        UUID id,
        UUID productId,
        String productName,
        int quantity
) {}
