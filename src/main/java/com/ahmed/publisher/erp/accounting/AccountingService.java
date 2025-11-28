package com.ahmed.publisher.erp.accounting;
import com.ahmed.publisher.erp.customerorder.CustomerOrder;
public interface AccountingService {
    void registerOrder(CustomerOrder order);
}