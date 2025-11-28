package com.ahmed.publisher.erp.fulfillment;
import com.ahmed.publisher.erp.customerorder.CustomerOrder;
public interface FulfillmentService {
    void scheduleForFulfillment(CustomerOrder order);
}