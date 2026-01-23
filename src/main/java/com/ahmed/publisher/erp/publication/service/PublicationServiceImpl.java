package com.ahmed.publisher.erp.publication.service;

import com.ahmed.publisher.erp.exceptions.http.ResourceNotFoundException;
import com.ahmed.publisher.erp.integration.bridge.client.BridgeCatalogClient;
import com.ahmed.publisher.erp.integration.bridge.mapper.PublicationSyncMapper;
import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import com.ahmed.publisher.erp.publication.entity.Publication;
import com.ahmed.publisher.erp.publication.repository.PublicationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private static final Logger log = LoggerFactory.getLogger(PublicationServiceImpl.class);

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
        log.info("Creating publication with ISBN={}", request.isbn());

        if (repository.existsByIsbn(request.isbn())) {
            log.warn("Publication creation failed: ISBN {} already exists", request.isbn());
            throw new IllegalStateException(
                    "Publication with ISBN " + request.isbn() + " already exists"
            );
        }

        Publication publication = new Publication();
        applyRequest(publication, request);

        publication = repository.save(publication);
        log.debug("Publication persisted id={}", publication.getId());

        createVariants(publication.getId(), request.variants());

        log.info("Syncing publication id={} to Bridge", publication.getId());
        bridgeClient.pushPublications(
                PublicationSyncMapper.toBridgeRequest(publication)
        );

        return toResponse(publication);
    }

    @Override
    public PublicationResponse update(UUID id, PublicationRequest request) {
        log.info("Updating publication id={}", id);

        Publication publication = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Publication update failed: id={} not found", id);
                    return new ResourceNotFoundException("Publication with id " + id + " not found");
                });

        applyRequest(publication, request);
        publication = repository.save(publication);

        log.info("Publication updated id={}, syncing to Bridge", id);
        bridgeClient.pushPublications(
                PublicationSyncMapper.toBridgeRequest(publication)
        );

        return toResponse(publication);
    }

    @Override
    public PublicationResponse getById(UUID id) {
        log.debug("Fetching publication id={}", id);

        Publication publication = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + id + " not found")
                );

        return toResponse(publication);
    }

    @Override
    public List<PublicationResponse> getAll() {
        log.debug("Fetching all publications");
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        log.info("Soft deleting publication id={}", id);

        Publication publication = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publication with id " + id + " not found")
                );

        publication.setDeleted(true);
        repository.save(publication);

        log.info("Publication marked as deleted id={}", id);
    }

    // =====================
    // Variant delegation
    // =====================

    @Override
    public PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request) {
        log.info("Creating variant for publicationId={}", publicationId);
        ensurePublicationExists(publicationId);
        return variantService.createVariant(publicationId, request);
    }

    @Override
    public PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request) {
        log.info("Updating variant id={} for publicationId={}", variantId, publicationId);
        ensurePublicationExists(publicationId);
        return variantService.updateVariant(publicationId, variantId, request);
    }

    @Override
    public PublicationVariantResponse getVariant(UUID publicationId, UUID variantId) {
        log.debug("Fetching variant id={} for publicationId={}", variantId, publicationId);
        ensurePublicationExists(publicationId);
        return variantService.getVariant(publicationId, variantId);
    }

    @Override
    public List<PublicationVariantResponse> getVariants(UUID publicationId) {
        log.debug("Fetching variants for publicationId={}", publicationId);
        ensurePublicationExists(publicationId);
        return variantService.getVariants(publicationId);
    }

    @Override
    public void deleteVariant(UUID publicationId, UUID variantId) {
        log.info("Deleting variant id={} for publicationId={}", variantId, publicationId);
        ensurePublicationExists(publicationId);
        variantService.deleteVariant(publicationId, variantId);
    }

    // =====================
    // Helpers
    // =====================

    private void ensurePublicationExists(UUID publicationId) {
        if (!repository.existsById(publicationId)) {
            log.warn("Publication id={} not found", publicationId);
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
            log.debug("No variants provided for publicationId={}", publicationId);
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
