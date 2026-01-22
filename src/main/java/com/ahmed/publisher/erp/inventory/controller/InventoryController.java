package com.ahmed.publisher.erp.inventory.controller;

import com.ahmed.publisher.erp.inventory.service.InventoryService;
import com.ahmed.publisher.erp.inventory.dto.InventoryAdjustmentRequest;
import com.ahmed.publisher.erp.inventory.entity.Inventory;
import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }

    @PostMapping("/{publicationId}/adjust")
    public Inventory adjustStock(@PathVariable UUID publicationId,
                                 @RequestBody InventoryAdjustmentRequest request) {
        return inventoryService.adjustStock(publicationId, request.delta(), request.reason(), null);
    }

    @GetMapping("/{publicationId}/adjustments")
    public List<InventoryAdjustment> getAdjustmentsForPublication(@PathVariable UUID publicationId) {
        log.debug("Getting adjustments for publicationId={}", publicationId);
        List<InventoryAdjustment> adjustments = inventoryService.getAdjustmentsForPublication(publicationId);
        log.info("Found {} adjustments for publicationId={}", adjustments.size(), publicationId);
        return adjustments;
    }

    @GetMapping("/adjustments")
    public List<InventoryAdjustment> getAllAdjustments() {
        return inventoryService.getAllAdjustments();
    }

}

