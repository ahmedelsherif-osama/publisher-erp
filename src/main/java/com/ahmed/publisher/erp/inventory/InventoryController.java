package com.ahmed.publisher.erp.inventory;

import com.ahmed.publisher.erp.inventory.dto.InventoryAdjustmentRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

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
        return inventoryService.getAdjustmentsForPublication(publicationId);
    }

    @GetMapping("/adjustments")
    public List<InventoryAdjustment> getAllAdjustments() {
        return inventoryService.getAllAdjustments();
    }

}

