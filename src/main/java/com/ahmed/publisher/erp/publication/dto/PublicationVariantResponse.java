package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PublicationVariantResponse(
        UUID id,
        UUID publicationId,
        String format,
        String language,
        BigDecimal price,
        int stockCount
) {}