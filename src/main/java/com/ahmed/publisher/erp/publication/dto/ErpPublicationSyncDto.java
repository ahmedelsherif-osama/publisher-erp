package com.ahmed.publisher.erp.publication.dto;

import java.util.List;

public record ErpPublicationSyncDto(
        String isbn,                  // business identifier
        String title,
        String author,
        List<ErpPublicationVariantSyncDto> variants
) {}
