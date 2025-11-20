package com.ahmed.publisher.erp.dto;

import java.util.UUID;

public record CreateOrderItemRequest(
        UUID productId,
        int quantity
) {}
