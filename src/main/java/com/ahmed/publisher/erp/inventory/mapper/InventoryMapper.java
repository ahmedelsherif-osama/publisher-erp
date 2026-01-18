package com.ahmed.publisher.erp.inventory.mapper;

import com.ahmed.publisher.erp.inventory.dto.InventoryAdjustmentResponse;
import com.ahmed.publisher.erp.inventory.dto.InventoryResponse;
import com.ahmed.publisher.erp.inventory.entity.Inventory;
import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;

public class InventoryMapper {

    public static InventoryResponse toResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getPublication().getId(),
                inventory.getQuantity(),
                inventory.getUpdatedAt()
        );
    }

    public static InventoryAdjustmentResponse toResponse(InventoryAdjustment adj) {
        return new InventoryAdjustmentResponse(
                adj.getId(),
                adj.getPublication().getId(),
                adj.getDelta(),
                adj.getReason(),
                adj.getReferenceId(),
                adj.getCreatedAt()
        );
    }
}
