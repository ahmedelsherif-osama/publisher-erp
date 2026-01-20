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

import com.ahmed.publisher.erp.exceptions.http.BadRequestException;
import com.ahmed.publisher.erp.exceptions.business.BusinessRuleViolationException;
import com.ahmed.publisher.erp.exceptions.http.ResourceNotFoundException;


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
                        new ResourceNotFoundException(
                                "Inventory not found for publication " + publicationId
                        )
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
            throw new BadRequestException("Stock adjustment delta cannot be zero");
        }

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Publication not found with id " + publicationId
                        )
                );

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
            throw new BusinessRuleViolationException(
                    "Insufficient stock for publication " + publicationId
            );
        }

        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);

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
