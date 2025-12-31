package com.ahmed.publisher.erp.inventory;

import com.ahmed.publisher.erp.publication.Publication;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class InventoryItem {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "publication_id",unique = true)
    private Publication publication;

    private int quantity;
}
