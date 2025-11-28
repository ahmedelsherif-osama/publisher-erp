package com.ahmed.publisher.erp.inventory;
import com.ahmed.publisher.erp.customerorder.CustomerOrder;
public interface InventoryReservationService {
    ReservationResult tryReserve(CustomerOrder order);
    record ReservationResult(ReservationStatus status, String message) {}
}

