package com.ahmed.publisher.erp.order.dto;

import com.ahmed.publisher.erp.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, UUID userId, List<OrderItemResponse> items, OrderStatus status, LocalDateTime createdAt) {}