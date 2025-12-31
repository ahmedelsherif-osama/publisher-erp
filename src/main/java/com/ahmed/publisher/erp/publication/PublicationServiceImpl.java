package com.ahmed.publisher.erp.publication;

import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository repository;

    public PublicationServiceImpl(PublicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public PublicationResponse create(PublicationRequest request) {
        if (repository.existsByIsbn(request.isbn())) {
            throw new IllegalArgumentException("Publication with ISBN already exists");
        }

        Publication publication = new Publication();
        applyRequest(publication, request);

        return toResponse(repository.save(publication));
    }

    @Override
    public PublicationResponse update(UUID id, PublicationRequest request) {
        Publication publication = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        applyRequest(publication, request);

        return toResponse(repository.save(publication));
    }

    @Override
    public PublicationResponse getById(UUID id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));
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
        repository.deleteById(id);
    }

    // ===== helpers =====

    private void applyRequest(Publication publication, PublicationRequest request) {
        publication.setTitle(request.title());
        publication.setIsbn(request.isbn());
        publication.setAuthor(request.author());
        publication.setPrice(request.price());
    }

    private PublicationResponse toResponse(Publication publication) {
        return new PublicationResponse(
                publication.getId(),
                publication.getTitle(),
                publication.getIsbn(),
                publication.getAuthor(),
                publication.getPrice()
        );
    }
}
