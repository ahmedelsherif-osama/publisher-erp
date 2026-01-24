package com.ahmed.publisher.erp.order.controller;

import com.ahmed.publisher.erp.order.dto.OrderCreateRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import com.ahmed.publisher.erp.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Orders", description = "Order management APIs")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log =
            LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // =====================
    // Create order
    // =====================

    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        log.info("Received request to create order with {} items",
                request.items().size());
        log.debug("OrderCreateRequest payload: {}", request);

        OrderResponse response = orderService.create(request.items());

        log.info("Order created successfully with id={}", response.id());
        return response;
    }

    // =====================
    // Get all orders
    // =====================

    @Operation(summary = "Get all orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR','VIEWER')")
    @GetMapping
    public List<OrderResponse> getOrders() {
        log.info("Received request to fetch all orders");

        List<OrderResponse> orders = orderService.getAllOrders();

        log.info("Returning {} orders", orders.size());
        return orders;
    }

    // =====================
    // Cancel order
    // =====================

    @Operation(summary = "Cancel an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled"),
            @ApiResponse(responseCode = "400", description = "Invalid order state"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @PostMapping("/{orderId}/cancel")
    public void cancel(@PathVariable UUID orderId) {
        log.info("Received request to cancel order with id={}", orderId);

        orderService.cancel(orderId);

        log.info("Order cancelled successfully with id={}", orderId);
    }

    // =====================
    // Complete order
    // =====================

    @Operation(summary = "Complete an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order completed"),
            @ApiResponse(responseCode = "400", description = "Invalid order state"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{orderId}/complete")
    public void complete(@PathVariable UUID orderId) {
        log.info("Received request to complete order with id={}", orderId);

        orderService.complete(orderId);

        log.info("Order completed successfully with id={}", orderId);
    }
}
