package com.ahmed.publisher.erp.product.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {}
