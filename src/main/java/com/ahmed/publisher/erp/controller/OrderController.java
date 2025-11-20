package com.ahmed.publisher.erp.controller;

import com.ahmed.publisher.erp.dto.CreateOrderRequest;
import com.ahmed.publisher.erp.dto.OrderDto;
import com.ahmed.publisher.erp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<OrderDto> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
