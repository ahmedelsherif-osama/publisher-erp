package com.ahmed.publisher.erp.dto;

import java.util.List;

public record CreateOrderRequest(
        List<CreateOrderItemRequest> items
) {}
