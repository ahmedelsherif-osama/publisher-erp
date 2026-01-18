package com.ahmed.publisher.erp.inventory.repository;

import com.ahmed.publisher.erp.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByPublicationId(UUID publicationId);
}
