package com.ahmed.publisher.erp.inventory.controller;

import com.ahmed.publisher.erp.inventory.dto.InventoryAdjustmentRequest;
import com.ahmed.publisher.erp.inventory.entity.Inventory;
import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;
import com.ahmed.publisher.erp.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Inventory", description = "Inventory management APIs")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // =====================
    // Get all inventory items
    // =====================
    @GetMapping
    public List<Inventory> getAll() {
        log.info("Request to fetch all inventory items");
        List<Inventory> inventories = inventoryService.getAll();
        log.info("Returning {} inventory items", inventories.size());
        return inventories;
    }

    // =====================
    // Adjust stock
    // =====================
    @PostMapping("/{publicationId}/adjust")
    public Inventory adjustStock(
            @PathVariable UUID publicationId,
            @RequestBody InventoryAdjustmentRequest request) {

        log.info("Request to adjust stock for publicationId={} by delta={}", publicationId, request.delta());
        log.debug("InventoryAdjustmentRequest payload: {}", request);

        Inventory inventory = inventoryService.adjustStock(
                publicationId,
                request.delta(),
                request.reason(),
                null
        );

        log.info("Stock adjusted for publicationId={}, new quantity={}", publicationId, inventory.getQuantity());
        return inventory;
    }

    // =====================
    // Get adjustments for a publication
    // =====================
    @GetMapping("/{publicationId}/adjustments")
    public List<InventoryAdjustment> getAdjustmentsForPublication(@PathVariable UUID publicationId) {
        log.info("Request to fetch inventory adjustments for publicationId={}", publicationId);
        List<InventoryAdjustment> adjustments = inventoryService.getAdjustmentsForPublication(publicationId);
        log.info("Returning {} adjustments for publicationId={}", adjustments.size(), publicationId);
        return adjustments;
    }

    // =====================
    // Get all adjustments
    // =====================
    @GetMapping("/adjustments")
    public List<InventoryAdjustment> getAllAdjustments() {
        log.info("Request to fetch all inventory adjustments");
        List<InventoryAdjustment> adjustments = inventoryService.getAllAdjustments();
        log.info("Returning {} total adjustments", adjustments.size());
        return adjustments;
    }
}
