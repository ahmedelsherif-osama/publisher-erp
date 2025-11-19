package com.ahmed.publisher.erp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

}
