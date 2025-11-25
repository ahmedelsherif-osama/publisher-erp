package com.ahmed.publisher.erp.customerorder.dto;

import java.util.UUID;

public record CustomerOrderItemDto(
        UUID id,
        UUID productId,
        String productName,
        int quantity
) {}
