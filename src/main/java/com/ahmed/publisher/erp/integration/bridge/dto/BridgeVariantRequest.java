package com.ahmed.publisher.erp.integration.bridge.dto;

import java.math.BigDecimal;

public record BridgeVariantRequest(
        String sku,
        BigDecimal price,
        int stock
) {}
