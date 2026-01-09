package com.ahmed.publisher.erp.publication;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PublicationVariant {
    @Id
    private UUID id;

    @ManyToOne
    private Publication publication;

    private String format;   // Hardcover, Paperback, eBook, Audiobook
    private String language; // EN, DE, etc.
    private BigDecimal price;
    private int stockCount;
}
