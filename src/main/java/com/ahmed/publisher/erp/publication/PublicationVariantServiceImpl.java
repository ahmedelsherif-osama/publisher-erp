package com.ahmed.publisher.erp.publication;

import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationVariantServiceImpl implements PublicationVariantService {

    private final PublicationVariantRepository variantRepository;
    private final PublicationRepository publicationRepository;

    public PublicationVariantServiceImpl(
            PublicationVariantRepository variantRepository,
            PublicationRepository publicationRepository
    ) {
        this.variantRepository = variantRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        PublicationVariant variant = new PublicationVariant();
        variant.setId(UUID.randomUUID());
        variant.setPublication(publication);
        variant.setFormat(request.format());
        variant.setLanguage(request.language());
        variant.setPrice(request.price());
        variant.setStockCount(request.stockCount());

        variantRepository.save(variant);
        return toResponse(variant);
    }

    @Override
    public PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request) {
        PublicationVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        if (!variant.getPublication().getId().equals(publicationId)) {
            throw new IllegalArgumentException("Variant does not belong to the publication");
        }

        variant.setFormat(request.format());
        variant.setLanguage(request.language());
        variant.setPrice(request.price());
        variant.setStockCount(request.stockCount());

        return toResponse(variant);
    }

    @Override
    public PublicationVariantResponse getVariant(UUID publicationId, UUID variantId) {
        PublicationVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        if (!variant.getPublication().getId().equals(publicationId)) {
            throw new IllegalArgumentException("Variant does not belong to the publication");
        }

        return toResponse(variant);
    }

    @Override
    public List<PublicationVariantResponse> getVariants(UUID publicationId) {
        return variantRepository.findByPublicationId(publicationId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteVariant(UUID publicationId, UUID variantId) {
        PublicationVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        if (!variant.getPublication().getId().equals(publicationId)) {
            throw new IllegalArgumentException("Variant does not belong to the publication");
        }

        variantRepository.delete(variant);
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
