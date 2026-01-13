package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;
import java.util.List;

public record PublicationRequest(
        String title,
        String isbn,
        String author,
        String description,
        List<PublicationVariantRequest> variants
) {}
