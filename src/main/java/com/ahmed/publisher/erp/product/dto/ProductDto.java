package com.ahmed.publisher.erp.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {}
