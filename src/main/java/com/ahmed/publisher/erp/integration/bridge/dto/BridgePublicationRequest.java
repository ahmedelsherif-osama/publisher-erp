package com.ahmed.publisher.erp.integration.bridge.dto;

import java.math.BigDecimal;
import java.util.List;

public record BridgePublicationRequest(
        String title,
        String isbn,
        String author,
        String description,
        List<BridgeVariantRequest> variants
) {}
