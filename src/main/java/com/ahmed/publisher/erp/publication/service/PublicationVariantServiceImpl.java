package com.ahmed.publisher.erp.publication.service;

import com.ahmed.publisher.erp.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.integration.bridge.client.BridgeCatalogClient;
import com.ahmed.publisher.erp.integration.bridge.mapper.PublicationSyncMapper;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import com.ahmed.publisher.erp.publication.entity.Publication;
import com.ahmed.publisher.erp.publication.entity.PublicationVariant;
import com.ahmed.publisher.erp.publication.repository.PublicationRepository;
import com.ahmed.publisher.erp.publication.repository.PublicationVariantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationVariantServiceImpl implements PublicationVariantService {

    private final PublicationVariantRepository variantRepository;
    private final PublicationRepository publicationRepository;
    private final BridgeCatalogClient bridgeClient;

    public PublicationVariantServiceImpl(
            PublicationVariantRepository variantRepository,
            PublicationRepository publicationRepository,
            BridgeCatalogClient bridgeClient
    ) {
        this.variantRepository = variantRepository;
        this.publicationRepository = publicationRepository;
        this.bridgeClient = bridgeClient;
    }

    @Override
    public PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request) {
        Publication publication = getPublicationOrThrow(publicationId);

        PublicationVariant variant = new PublicationVariant();
        variant.setId(UUID.randomUUID());
        variant.setPublication(publication);
        variant.setFormat(request.format());
        variant.setLanguage(request.language());
        variant.setPrice(request.price());
        variant.setStockCount(request.stockCount());

        variantRepository.save(variant);

        bridgeClient.pushPublications(PublicationSyncMapper.toBridgeRequest(publication));

        return toResponse(variant);
    }

    @Override
    public PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request) {
        PublicationVariant variant = getVariantOrThrow(variantId, publicationId);

        variant.setFormat(request.format());
        variant.setLanguage(request.language());
        variant.setPrice(request.price());
        variant.setStockCount(request.stockCount());

        variantRepository.save(variant);

        bridgeClient.pushPublications(PublicationSyncMapper.toBridgeRequest(variant.getPublication()));

        return toResponse(variant);
    }

    @Override
    public PublicationVariantResponse getVariant(UUID publicationId, UUID variantId) {
        PublicationVariant variant = getVariantOrThrow(variantId, publicationId);
        return toResponse(variant);
    }

    @Override
    public List<PublicationVariantResponse> getVariants(UUID publicationId) {
        Publication publication = getPublicationOrThrow(publicationId);

        return variantRepository.findByPublicationId(publicationId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteVariant(UUID publicationId, UUID variantId) {
        PublicationVariant variant = getVariantOrThrow(variantId, publicationId);
        variantRepository.delete(variant);
    }

    // =========================
    // Helpers
    // =========================

    private Publication getPublicationOrThrow(UUID publicationId) {
        return publicationRepository.findById(publicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + publicationId + " not found")
                );
    }

    private PublicationVariant getVariantOrThrow(UUID variantId, UUID publicationId) {
        PublicationVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Variant with id " + variantId + " not found")
                );

        if (!variant.getPublication().getId().equals(publicationId)) {
            throw new IllegalStateException(
                    "Variant " + variantId + " does not belong to publication " + publicationId
            );
        }

        return variant;
    }

    private PublicationVariantResponse toResponse(PublicationVariant variant) {
        return new PublicationVariantResponse(
                variant.getId(),
                variant.getPublication().getId(),
                variant.getFormat(),
                variant.getLanguage(),
                variant.getPrice(),
                variant.getStockCount()
        );
    }
}
