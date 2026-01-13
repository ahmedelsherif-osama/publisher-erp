package com.ahmed.publisher.erp.publication;

import com.ahmed.publisher.erp.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.integration.bridge.BridgeCatalogClient;
import com.ahmed.publisher.erp.integration.bridge.PublicationSyncMapper;
import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {
    private final BridgeCatalogClient bridgeClient;
    private final PublicationRepository repository;
    private final PublicationVariantService variantService;

    public PublicationServiceImpl(
            PublicationRepository repository,
            PublicationVariantService variantService,
            BridgeCatalogClient bridgeClient
    ) {
        this.repository = repository;
        this.variantService = variantService;
        this.bridgeClient = bridgeClient;
    }

    // ===== Publication CRUD =====

    @Override
    public PublicationResponse create(PublicationRequest request) {
        if (repository.existsByIsbn(request.isbn())) {
            throw new IllegalArgumentException("Publication with ISBN already exists");
        }

        Publication publication = new Publication();
        applyRequest(publication, request);


        publication = repository.save(publication);

        bridgeClient.pushPublications(
              PublicationSyncMapper.toBridgeRequest(publication)
        );

        return toResponse(publication);
    }


    @Override
    public PublicationResponse update(UUID id, PublicationRequest request) {
        Publication publication = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

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
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        return toResponse(publication);
    }

    @Override
    public List<PublicationResponse> getAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Publication not found");
        }
        Publication publication = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        publication.setDeleted(true);
        repository.save(publication);
    }

    // ===== Variant delegation =====

    @Override
    public PublicationVariantResponse createVariant(UUID publicationId, PublicationVariantRequest request) {
        return variantService.createVariant(publicationId, request);
    }

    @Override
    public PublicationVariantResponse updateVariant(UUID publicationId, UUID variantId, PublicationVariantRequest request) {
        return variantService.updateVariant(publicationId, variantId, request);
    }

    @Override
    public PublicationVariantResponse getVariant(UUID publicationId, UUID variantId) {
        return variantService.getVariant(publicationId, variantId);
    }

    @Override
    public List<PublicationVariantResponse> getVariants(UUID publicationId) {
        return variantService.getVariants(publicationId);
    }

    @Override
    public void deleteVariant(UUID publicationId, UUID variantId) {
        variantService.deleteVariant(publicationId, variantId);
    }

    // ===== Helpers =====

    private void applyRequest(Publication publication, PublicationRequest request) {
        publication.setTitle(request.title());
        publication.setIsbn(request.isbn());
        publication.setAuthor(request.author());
        publication.setDescription(request.description());
        for (PublicationVariantRequest v : request.variants()) {
            variantService.createVariant(publication.getId(), v);
        }
    }

    private PublicationResponse toResponse(Publication publication) {
        // Include all variants
        List<PublicationVariantResponse> variants = variantService.getVariants(publication.getId());

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
