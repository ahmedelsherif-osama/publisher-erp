package com.ahmed.publisher.erp.integration.bridge;

import com.ahmed.publisher.erp.integration.bridge.dto.BridgePublicationRequest;
import com.ahmed.publisher.erp.integration.bridge.dto.BridgeVariantRequest;
import com.ahmed.publisher.erp.publication.Publication;
import com.ahmed.publisher.erp.publication.PublicationVariant;

import java.util.List;

public class PublicationSyncMapper {

    public static BridgePublicationRequest toBridgeRequest(Publication publication) {
        List<BridgeVariantRequest> variants =
                publication.getVariants().stream()
                        .map(PublicationSyncMapper::toBridgeVariant)
                        .toList();

        return new BridgePublicationRequest(
                publication.getTitle(),
                publication.getIsbn(),
                publication.getAuthor(),
                publication.getDescription(),
                variants
        );
    }

    private static BridgeVariantRequest toBridgeVariant(PublicationVariant variant) {
        return new BridgeVariantRequest(
                variant.getSku(),
                variant.getPrice(),
                variant.getStockCount()
        );
    }
}
