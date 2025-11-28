package com.ahmed.publisher.erp.customerorder;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="customer_orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id @GeneratedValue @UuidGenerator
    private UUID id;

    private UUID customerId;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String source; // STOREFRONT, MARKETPLACE

    private String currency;
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerOrderItem> items = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        this.totalAmount = items.stream()
                .map(CustomerOrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
