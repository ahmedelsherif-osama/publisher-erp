package com.ahmed.publisher.erp.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<Order, UUID> {
}
