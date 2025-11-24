package com.ahmed.publisher.erp.order.dto;

import java.util.UUID;

public record CreateOrderItemRequest(
        UUID productId,
        int quantity
) {}
