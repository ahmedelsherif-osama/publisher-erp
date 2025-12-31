package com.ahmed.publisher.erp.inventory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryAdjustmentResponse(
        UUID id,
        UUID publicationId,
        int delta,
        String reason,
        UUID referenceId,
        LocalDateTime createdAt
) {}
