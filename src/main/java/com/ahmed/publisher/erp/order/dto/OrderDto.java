package com.ahmed.publisher.erp.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID id,
        LocalDateTime createdAt,
        List<OrderItemDto> items
) {}
