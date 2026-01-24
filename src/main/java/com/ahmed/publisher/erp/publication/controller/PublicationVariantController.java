package com.ahmed.publisher.erp.publication.controller;

import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import com.ahmed.publisher.erp.publication.service.PublicationVariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Publication Variants", description = "Publication variant management APIs")
@RestController
@RequestMapping("/api/publications/{publicationId}/variants")
public class PublicationVariantController {

    private static final Logger log = LoggerFactory.getLogger(PublicationVariantController.class);

    private final PublicationVariantService variantService;

    public PublicationVariantController(PublicationVariantService variantService) {
        this.variantService = variantService;
    }

    // =====================
    // Create variant
    // =====================
    @Operation(summary = "Create a new variant for a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @PostMapping
    public PublicationVariantResponse create(
            @PathVariable UUID publicationId,
            @RequestBody PublicationVariantRequest request) {

        log.info("Request to create variant for publicationId={}", publicationId);
        log.debug("PublicationVariantRequest payload: {}", request);

        PublicationVariantResponse response = variantService.createVariant(publicationId, request);

        log.info("Variant created with id={} for publicationId={}", response.id(), publicationId);
        return response;
    }

    // =====================
    // Update variant
    // =====================
    @Operation(summary = "Update a variant for a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Variant or publication not found")
    })
    @PutMapping("/{variantId}")
    public PublicationVariantResponse update(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId,
            @RequestBody PublicationVariantRequest request) {

        log.info("Request to update variant id={} for publicationId={}", variantId, publicationId);
        log.debug("PublicationVariantRequest payload: {}", request);

        PublicationVariantResponse response = variantService.updateVariant(publicationId, variantId, request);

        log.info("Variant updated id={} for publicationId={}", variantId, publicationId);
        return response;
    }

    // =====================
    // Get variant by ID
    // =====================
    @Operation(summary = "Get a variant by ID for a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Variant or publication not found")
    })
    @GetMapping("/{variantId}")
    public PublicationVariantResponse get(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId) {

        log.info("Request to fetch variant id={} for publicationId={}", variantId, publicationId);
        return variantService.getVariant(publicationId, variantId);
    }

    // =====================
    // Get all variants
    // =====================
    @Operation(summary = "Get all variants for a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variants fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping
    public List<PublicationVariantResponse> getAll(@PathVariable UUID publicationId) {
        log.info("Request to fetch all variants for publicationId={}", publicationId);
        List<PublicationVariantResponse> variants = variantService.getVariants(publicationId);
        log.info("Returning {} variants for publicationId={}", variants.size(), publicationId);
        return variants;
    }

    // =====================
    // Delete variant
    // =====================
    @Operation(summary = "Delete a variant for a publication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Variant or publication not found")
    })
    @DeleteMapping("/{variantId}")
    public void delete(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId) {

        log.info("Request to delete variant id={} for publicationId={}", variantId, publicationId);
        variantService.deleteVariant(publicationId, variantId);
        log.info("Variant deleted id={} for publicationId={}", variantId, publicationId);
    }
}
