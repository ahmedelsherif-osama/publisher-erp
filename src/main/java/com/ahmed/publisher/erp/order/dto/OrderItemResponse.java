package com.ahmed.publisher.erp.order.dto;

import java.util.UUID;

public record OrderItemResponse(UUID publicationId, int quantity) {}