package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;

public record PublicationVariantRequest(
        String format,
        String language,
        BigDecimal price,
        int stockCount
) {}