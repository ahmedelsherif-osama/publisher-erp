package com.ahmed.publisher.erp.order;

import com.ahmed.publisher.erp.order.dto.CreateOrderRequest;
import com.ahmed.publisher.erp.order.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(CreateOrderRequest request);
    OrderDto findById(UUID id);
    List<OrderDto> findAll();
    void delete(UUID id);
}
