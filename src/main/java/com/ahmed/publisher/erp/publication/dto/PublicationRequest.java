package com.ahmed.publisher.erp.publication.dto;

import java.math.BigDecimal;

public record PublicationRequest(
        String title,
        String isbn,
        String author,
        BigDecimal price
) {}
