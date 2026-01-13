package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PublicationResponse(
        UUID id,
        String title,
        String isbn,
        String author,
        String description,
        List<PublicationVariantResponse> variants
) {}
