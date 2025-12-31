package com.ahmed.publisher.erp.inventory;

import com.ahmed.publisher.erp.publication.Publication;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class InventoryAdjustment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Publication publication;

    private int delta; // +10, -3, etc.

    private String reason; 
    // e.g. "ORDER_CREATED", "ORDER_CANCELLED", "MANUAL_ADJUSTMENT"

    private UUID referenceId;
    // orderId if related to an order

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
