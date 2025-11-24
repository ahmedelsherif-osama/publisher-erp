package com.ahmed.publisher.erp.order.dto;

import java.util.List;

public record CreateOrderRequest(
        List<CreateOrderItemRequest> items
) {}
