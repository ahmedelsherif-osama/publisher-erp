package com.ahmed.publisher.erp.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryAdjustmentRepository extends JpaRepository <InventoryAdjustment, UUID> {
    List<InventoryAdjustment> findByPublicationIdOrderByCreatedAtDesc(UUID publicationId);
}
