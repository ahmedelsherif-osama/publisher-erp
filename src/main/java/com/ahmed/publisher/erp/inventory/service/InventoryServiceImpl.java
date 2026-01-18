package com.ahmed.publisher.erp.inventory.service;

import com.ahmed.publisher.erp.inventory.repository.InventoryAdjustmentRepository;
import com.ahmed.publisher.erp.inventory.repository.InventoryRepository;
import com.ahmed.publisher.erp.inventory.entity.Inventory;
import com.ahmed.publisher.erp.inventory.entity.InventoryAdjustment;
import com.ahmed.publisher.erp.publication.entity.Publication;
import com.ahmed.publisher.erp.publication.repository.PublicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryAdjustmentRepository adjustmentRepository;
    private final PublicationRepository publicationRepository;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            InventoryAdjustmentRepository adjustmentRepository,
            PublicationRepository publicationRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.adjustmentRepository = adjustmentRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Inventory getByPublication(UUID publicationId) {
        return inventoryRepository.findByPublicationId(publicationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Inventory not found for publication")
                );
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory adjustStock(
            UUID publicationId,
            int delta,
            String reason,
            UUID referenceId
    ) {
        if (delta == 0) {
            throw new IllegalArgumentException("Delta cannot be zero");
        }

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        Inventory inventory = inventoryRepository
                .findByPublicationId(publicationId)
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setPublication(publication);
                    inv.setQuantity(0);
                    return inv;
                });

        int newQuantity = inventory.getQuantity() + delta;

        if (newQuantity < 0) {
            throw new IllegalStateException(
                    "Insufficient stock for publication " + publicationId
            );
        }

        // update state
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);

        // record history
        InventoryAdjustment adjustment = new InventoryAdjustment();
        adjustment.setPublication(publication);
        adjustment.setDelta(delta);
        adjustment.setReason(reason);
        adjustment.setReferenceId(referenceId);

        adjustmentRepository.save(adjustment);

        return inventory;
    }

    @Override
    public List<InventoryAdjustment> getAllAdjustments() {
        return adjustmentRepository.findAll();
    }


    @Override
    public List<InventoryAdjustment> getAdjustmentsForPublication(UUID publicationId) {
        return adjustmentRepository.findByPublicationIdOrderByCreatedAtDesc(publicationId);
    }
}
