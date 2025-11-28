package com.ahmed.publisher.erp.customerorder;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CustomerOrderItem {
    @Id @GeneratedValue @UuidGenerator
    private UUID id;

    private UUID productId;
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
    private boolean reserved;
}
