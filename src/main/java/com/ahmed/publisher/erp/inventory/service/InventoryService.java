package com.ahmed.publisher.erp.inventory.service;

import com.ahmed.publisher.erp.inventory.entity.Inventory;
import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    Inventory getByPublication(UUID publicationId);

    List<Inventory> getAll();

    Inventory adjustStock(
            UUID publicationId,
            int delta,
            String reason,
            UUID referenceId
    );

    List<InventoryAdjustment> getAllAdjustments();

    List<InventoryAdjustment> getAdjustmentsForPublication(UUID publicationId);

}

