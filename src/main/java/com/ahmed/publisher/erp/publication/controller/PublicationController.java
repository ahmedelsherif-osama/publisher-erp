package com.ahmed.publisher.erp.publication.controller;

import com.ahmed.publisher.erp.publication.service.PublicationService;
import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
@PreAuthorize("isAuthenticated()")
public class PublicationController {

    private static final Logger log = LoggerFactory.getLogger(PublicationController.class);

    private final PublicationService service;

    public PublicationController(PublicationService service) {
        this.service = service;
    }

    // ===== Publication CRUD =====

    @PostMapping
    public PublicationResponse create(@RequestBody PublicationRequest request) {
        log.info("Request to create publication");
        log.debug("PublicationRequest payload: {}", request);

        PublicationResponse response = service.create(request);

        log.info("Publication created with id={}", response.id());
        return response;
    }

    @PutMapping("/{id}")
    public PublicationResponse update(
            @PathVariable UUID id,
            @RequestBody PublicationRequest request) {

        log.info("Request to update publication id={}", id);
        log.debug("PublicationRequest payload: {}", request);

        PublicationResponse response = service.update(id, request);

        log.info("Publication updated id={}", id);
        return response;
    }

    @GetMapping("/{id}")
    public PublicationResponse getById(@PathVariable UUID id) {
        log.info("Request to fetch publication id={}", id);
        return service.getById(id);
    }

    @GetMapping
    public List<PublicationResponse> getAll() {
        log.info("Request to fetch all publications");
        List<PublicationResponse> publications = service.getAll();
        log.info("Returning {} publications", publications.size());
        return publications;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        log.info("Request to delete publication id={}", id);
        service.delete(id);
        log.info("Publication deleted id={}", id);
    }
}
