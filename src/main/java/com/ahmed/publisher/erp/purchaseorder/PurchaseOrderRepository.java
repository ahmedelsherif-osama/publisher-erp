package com.ahmed.publisher.erp.purchaseorder;

import com.ahmed.publisher.erp.customerorder.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<CustomerOrder, UUID> {
}
