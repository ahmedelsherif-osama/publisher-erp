package com.ahmed.publisher.erp.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
public record UpdateProductRequest(
       @NotNull String name,
       @NotNull String description,
       @NotNull BigDecimal price,
       @NotNull Integer stockQuantity
) {}
