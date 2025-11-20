package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.dto.CreateOrderRequest;
import com.ahmed.publisher.erp.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(CreateOrderRequest request);
    OrderDto findById(UUID id);
    List<OrderDto> findAll();
    void delete(UUID id);
}
