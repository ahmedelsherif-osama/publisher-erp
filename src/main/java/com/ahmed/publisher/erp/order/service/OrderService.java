package com.ahmed.publisher.erp.order.service;

import com.ahmed.publisher.erp.order.dto.OrderItemRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(List<OrderItemRequest> items);
    List<OrderResponse> getAllOrders();
    void cancel(UUID orderId);
    void complete(UUID orderId);

}
