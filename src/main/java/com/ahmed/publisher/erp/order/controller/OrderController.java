package com.ahmed.publisher.erp.order.controller;

import com.ahmed.publisher.erp.order.service.OrderService;
import com.ahmed.publisher.erp.order.dto.OrderCreateRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public void cancel(@PathVariable UUID orderId) {
        log.info("Received request to cancel order with id={}", orderId);
        orderService.cancel(orderId);
        log.info("Cancel request processed for order id={}", orderId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        log.info("Received request to create order for {} items", request.items().size());
        log.debug("OrderCreateRequest payload: {}", request);
        OrderResponse response = orderService.create(request.items());
        log.info("Order created successfully with id={}", response.id());
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDITOR','VIEWER')")
    @GetMapping
    public List<OrderResponse> getOrders() {
        log.info("Received request to fetch all orders");
        List<OrderResponse> orders = orderService.getAllOrders();
        log.info("Returning {} orders", orders.size());
        return orders;
    }

    @PostMapping("/{orderId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public void complete(@PathVariable UUID orderId) {
        log.info("Received request to complete order with id={}", orderId);
        orderService.complete(orderId);
        log.info("Order completed successfully with id={}", orderId);
    }
}
