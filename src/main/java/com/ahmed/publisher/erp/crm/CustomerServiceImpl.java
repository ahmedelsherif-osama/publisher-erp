package com.ahmed.publisher.erp.crm;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository){
        this.repository=repository;
    }

    /**
     * @param customerId 
     * @return
     */
    @Override
    public CustomerDto findByIdOrThrow(UUID customerId) {
        return repository.findById(customerId)
                .map(CustomerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    /**
     * @param request 
     * @return
     */
    @Override
    public CustomerDto create(CreateCustomerRequest request) {
        Customer entity = CustomerMapper.toEntity(request);
        Customer saved= repository.save(entity);
        return CustomerMapper.toDto(saved);
    }

    /**
     * @param request 
     * @return
     */
    @Override
    public CustomerDto updatePartially(CreateCustomerRequest request) {
        return null;
    }

    /**
     * @param request 
     * @return
     */
    @Override
    public CustomerDto updateFully(CreateCustomerRequest request) {
        return null;
    }

    /**
     * @return 
     */
    @Override
    public List<CustomerDto> findAll() {
        var customers= repository.findAll();
        return customers.stream().map(CustomerMapper::toDto).toList();
    }
}
