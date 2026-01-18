package com.ahmed.publisher.erp.publication.service;

import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;

import java.util.List;
import java.util.UUID;

public interface PublicationVariantService {

    PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request);
    PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request);
    PublicationVariantResponse getVariant(UUID publicationId, UUID variantId);
    List<PublicationVariantResponse> getVariants(UUID publicationId);
    void deleteVariant(UUID publicationId, UUID variantId);
}
