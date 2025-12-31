package com.ahmed.publisher.erp.publication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Publication {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String isbn;
    private String author;
    private BigDecimal price;
}
