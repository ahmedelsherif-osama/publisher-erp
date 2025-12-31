package com.ahmed.publisher.erp.inventory.dto;

import java.util.UUID;

public record InventoryAdjustmentRequest(
        int delta,
        String reason,
        UUID referenceId // optional
) {}
