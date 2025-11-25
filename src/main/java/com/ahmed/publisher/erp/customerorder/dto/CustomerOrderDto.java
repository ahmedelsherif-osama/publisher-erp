package com.ahmed.publisher.erp.customerorder.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerOrderDto(
        UUID id,
        LocalDateTime createdAt,
        List<CustomerOrderItemDto> items
) {}
