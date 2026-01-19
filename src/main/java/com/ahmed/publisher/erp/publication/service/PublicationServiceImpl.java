package com.ahmed.publisher.erp.publication.service;

import com.ahmed.publisher.erp.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.integration.bridge.client.BridgeCatalogClient;
import com.ahmed.publisher.erp.integration.bridge.mapper.PublicationSyncMapper;
import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import com.ahmed.publisher.erp.publication.entity.Publication;
import com.ahmed.publisher.erp.publication.repository.PublicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository repository;
    private final PublicationVariantService variantService;
    private final BridgeCatalogClient bridgeClient;

    public PublicationServiceImpl(
            PublicationRepository repository,
            PublicationVariantService variantService,
            BridgeCatalogClient bridgeClient
    ) {
        this.repository = repository;
        this.variantService = variantService;
        this.bridgeClient = bridgeClient;
    }

    // =====================
    // Publication CRUD
    // =====================

    @Override
    public PublicationResponse create(PublicationRequest request) {
        if (repository.existsByIsbn(request.isbn())) {
            throw new IllegalStateException(
                    "Publication with ISBN " + request.isbn() + " already exists"
            );
        }

        Publication publication = new Publication();
        applyRequest(publication, request);

        publication = repository.save(publication);

        createVariants(publication.getId(), request.variants());

        bridgeClient.pushPublications(
                PublicationSyncMapper.toBridgeRequest(publication)
        );

        return toResponse(publication);
    }

    @Override
    public PublicationResponse update(UUID id, PublicationRequest request) {
        Publication publication = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + id + " not found")
                );

        applyRequest(publication, request);
        publication = repository.save(publication);

        bridgeClient.pushPublications(
                PublicationSyncMapper.toBridgeRequest(publication)
        );

        return toResponse(publication);
    }

    @Override
    public PublicationResponse getById(UUID id) {
        Publication publication = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + id + " not found")
                );

        return toResponse(publication);
    }

    @Override
    public List<PublicationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        Publication publication = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + id + " not found")
                );

        publication.setDeleted(true);
        repository.save(publication);
    }

    // =====================
    // Variant delegation
    // =====================

    @Override
    public PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request) {
        ensurePublicationExists(publicationId);
        return variantService.createVariant(publicationId, request);
    }

    @Override
    public PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request) {
        ensurePublicationExists(publicationId);
        return variantService.updateVariant(publicationId, variantId, request);
    }

    @Override
    public PublicationVariantResponse getVariant(UUID publicationId, UUID variantId) {
        ensurePublicationExists(publicationId);
        return variantService.getVariant(publicationId, variantId);
    }

    @Override
    public List<PublicationVariantResponse> getVariants(UUID publicationId) {
        ensurePublicationExists(publicationId);
        return variantService.getVariants(publicationId);
    }

    @Override
    public void deleteVariant(UUID publicationId, UUID variantId) {
        ensurePublicationExists(publicationId);
        variantService.deleteVariant(publicationId, variantId);
    }

    // =====================
    // Helpers
    // =====================

    private void ensurePublicationExists(UUID publicationId) {
        if (!repository.existsById(publicationId)) {
            throw new ResourceNotFoundException(
                    "Publication with id " + publicationId + " not found"
            );
        }
    }

    private void applyRequest(Publication publication, PublicationRequest request) {
        publication.setTitle(request.title());
        publication.setIsbn(request.isbn());
        publication.setAuthor(request.author());
        publication.setDescription(request.description());
    }

    private void createVariants(UUID publicationId, List<PublicationVariantRequest> variants) {
        if (variants == null || variants.isEmpty()) {
            return;
        }
        variants.forEach(v -> variantService.createVariant(publicationId, v));
    }

    private PublicationResponse toResponse(Publication publication) {
        List<PublicationVariantResponse> variants =
                variantService.getVariants(publication.getId());

        return new PublicationResponse(
                publication.getId(),
                publication.getTitle(),
                publication.getIsbn(),
                publication.getAuthor(),
                publication.getDescription(),
                variants
        );
    }
}
