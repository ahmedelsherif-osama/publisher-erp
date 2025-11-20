package com.ahmed.publisher.erp.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record PatchProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {}
