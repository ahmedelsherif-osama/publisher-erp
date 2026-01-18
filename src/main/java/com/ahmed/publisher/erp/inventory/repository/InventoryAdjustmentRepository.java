package com.ahmed.publisher.erp.inventory.repository;

import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryAdjustmentRepository extends JpaRepository <InventoryAdjustment, UUID> {
    List<InventoryAdjustment> findByPublicationIdOrderByCreatedAtDesc(UUID publicationId);
}
