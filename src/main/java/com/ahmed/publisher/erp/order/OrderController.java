package com.ahmed.publisher.erp.order;

import com.ahmed.publisher.erp.order.dto.OrderCreateRequest;
import com.ahmed.publisher.erp.order.dto.OrderItemRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public void cancel(@PathVariable UUID orderId) {
        orderService.cancel(orderId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return orderService.create(request.items());
    }


    @PreAuthorize("hasAnyRole('ADMIN','EDITOR','VIEWER')")
    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/{orderId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public void complete(@PathVariable UUID orderId) {
        orderService.complete(orderId);
    }


}
