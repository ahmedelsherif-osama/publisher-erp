package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderService {
    CustomerOrderDto create(CreateCustomerOrderRequest request);
    CustomerOrderDto findById(UUID id);
    List<CustomerOrderDto> findAll();
    void delete(UUID id);
}
