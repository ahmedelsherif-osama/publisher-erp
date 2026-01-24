package com.ahmed.publisher.erp.publication.controller;

import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;
import com.ahmed.publisher.erp.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Publications", description = "Publication management APIs")
@RestController
@RequestMapping("/api/publications")
@PreAuthorize("isAuthenticated()")
public class PublicationController {

    private static final Logger log = LoggerFactory.getLogger(PublicationController.class);

    private final PublicationService service;

    public PublicationController(PublicationService service) {
        this.service = service;
    }

    // =====================
    // Create publication
    // =====================
    @Operation(summary = "Create a new publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publication created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public PublicationResponse create(@RequestBody PublicationRequest request) {
        log.info("Request to create publication");
        log.debug("PublicationRequest payload: {}", request);

        PublicationResponse response = service.create(request);

        log.info("Publication created with id={}", response.id());
        return response;
    }

    // =====================
    // Update publication
    // =====================
    @Operation(summary = "Update an existing publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publication updated successfully"),
            @ApiResponse(responseCode = "404", description = "Publication not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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

    // =====================
    // Get publication by ID
    // =====================
    @Operation(summary = "Get a publication by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publication fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Publication not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public PublicationResponse getById(@PathVariable UUID id) {
        log.info("Request to fetch publication id={}", id);
        return service.getById(id);
    }

    // =====================
    // Get all publications
    // =====================
    @Operation(summary = "Get all publications")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publications fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<PublicationResponse> getAll() {
        log.info("Request to fetch all publications");
        List<PublicationResponse> publications = service.getAll();
        log.info("Returning {} publications", publications.size());
        return publications;
    }

    // =====================
    // Delete publication
    // =====================
    @Operation(summary = "Delete a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publication deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Publication not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        log.info("Request to delete publication id={}", id);
        service.delete(id);
        log.info("Publication deleted id={}", id);
    }
}
