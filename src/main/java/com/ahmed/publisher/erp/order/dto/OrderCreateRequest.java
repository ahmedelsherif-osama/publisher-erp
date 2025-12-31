package com.ahmed.publisher.erp.order.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
        List<OrderItemRequest> items
) {}
