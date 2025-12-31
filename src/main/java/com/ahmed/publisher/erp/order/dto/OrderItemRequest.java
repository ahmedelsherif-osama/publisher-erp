package com.ahmed.publisher.erp.order.dto;

import java.util.UUID;

public record OrderItemRequest(UUID publicationId, int quantity) {}
