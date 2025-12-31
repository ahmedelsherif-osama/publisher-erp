package com.ahmed.publisher.erp.inventory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponse(
        UUID publicationId,
        int quantity,
        LocalDateTime updatedAt
) {}
