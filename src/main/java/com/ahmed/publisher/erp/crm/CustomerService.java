package com.ahmed.publisher.erp.crm;

import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerDto findByIdOrThrow(UUID customerId);
    CustomerDto create(CreateCustomerRequest request);
    CustomerDto updatePartially(CreateCustomerRequest request);
    CustomerDto updateFully(CreateCustomerRequest request);
    List<CustomerDto> findAll();



}