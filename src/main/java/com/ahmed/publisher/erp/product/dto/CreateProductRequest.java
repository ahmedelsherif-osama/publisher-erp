package com.ahmed.publisher.erp.product.dto;

import java.math.BigDecimal;

public record   CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        int initialStock
) {}
