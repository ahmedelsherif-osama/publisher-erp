package com.ahmed.publisher.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private String description;
    private double price;
    private int stockQuantity;
}
