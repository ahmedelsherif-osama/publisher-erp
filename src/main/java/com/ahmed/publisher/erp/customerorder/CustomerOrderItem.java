package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class CustomerOrderItem {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Product product;

    private int quantity;
}
