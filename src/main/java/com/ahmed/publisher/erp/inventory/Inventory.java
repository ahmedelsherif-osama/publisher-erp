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
public class Inventory {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Publication publication;

    private int quantity;

    private LocalDateTime updatedAt;

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
