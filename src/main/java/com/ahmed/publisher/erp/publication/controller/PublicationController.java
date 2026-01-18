package com.ahmed.publisher.erp.publication.controller;

import com.ahmed.publisher.erp.publication.service.PublicationService;
import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
//@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("isAuthenticated()")
public class PublicationController {

    private final PublicationService service;

    public PublicationController(PublicationService service) {
        this.service = service;
    }

    // ===== Publication CRUD =====

    @PostMapping
    public PublicationResponse create(@RequestBody PublicationRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public PublicationResponse update(
            @PathVariable UUID id,
            @RequestBody PublicationRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    public PublicationResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PublicationResponse> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
