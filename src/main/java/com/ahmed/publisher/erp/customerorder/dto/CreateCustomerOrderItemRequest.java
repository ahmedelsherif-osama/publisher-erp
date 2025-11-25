package com.ahmed.publisher.erp.customerorder.dto;

import java.util.UUID;

public record CreateCustomerOrderItemRequest(
        UUID productId,
        int quantity
) {}
