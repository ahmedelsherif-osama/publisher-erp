package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService service;

    public CustomerOrderController(CustomerOrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOrderDto create(@RequestBody CreateCustomerOrderRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerOrderDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<CustomerOrderDto> findAll() {
        return service.findAll();
    }

//    @PatchMapping("/{id}/cancel")
//    public CustomerOrderDto cancel(@PathVariable UUID id) {  }




}
