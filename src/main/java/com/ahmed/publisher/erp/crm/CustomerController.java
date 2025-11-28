package com.ahmed.publisher.erp.crm;

import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@RequestBody CreateCustomerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable UUID id) {
        return service.findByIdOrThrow(id);
    }

    @GetMapping
    public List<CustomerDto> findAll() {
        return service.findAll();
    }

//    @PatchMapping("/{id}/cancel")
//    public CustomerOrderDto cancel(@PathVariable UUID id) {  }




}
