package com.ahmed.publisher.erp.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
}
