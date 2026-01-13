package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;

public record ErpPublicationVariantSyncDto(
        String sku,          // business identifier
        String format,       // Hardcover, Paperback, eBook
        String language,     // EN, DE, etc.
        BigDecimal price,
        int stockCount
) {}
