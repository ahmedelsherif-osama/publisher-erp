package com.ahmed.publisher.erp.publication;

import com.ahmed.publisher.erp.publication.dto.PublicationVariantRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationVariantResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications/{publicationId}/variants")
public class PublicationVariantController {

    private final PublicationVariantService variantService;

    public PublicationVariantController(PublicationVariantService variantService) {
        this.variantService = variantService;
    }

    @PostMapping
    public PublicationVariantResponse create(
            @PathVariable UUID publicationId,
            @RequestBody PublicationVariantRequest request) {
        return variantService.createVariant(publicationId, request);
    }

    @PutMapping("/{variantId}")
    public PublicationVariantResponse update(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId,
            @RequestBody PublicationVariantRequest request) {
        return variantService.updateVariant(publicationId, variantId, request);
    }

    @GetMapping("/{variantId}")
    public PublicationVariantResponse get(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId) {
        return variantService.getVariant(publicationId, variantId);
    }

    @GetMapping
    public List<PublicationVariantResponse> getAll(@PathVariable UUID publicationId) {
        return variantService.getVariants(publicationId);
    }

    @DeleteMapping("/{variantId}")
    public void delete(
            @PathVariable UUID publicationId,
            @PathVariable UUID variantId) {
        variantService.deleteVariant(publicationId, variantId);
    }
}
