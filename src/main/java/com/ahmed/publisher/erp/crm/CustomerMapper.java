package com.ahmed.publisher.erp.crm;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        if (customer == null) return null;

        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    public static Customer toEntity(CreateCustomerRequest request) {
        if (request == null) return null;

        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        return customer;
    }

    public static void updateEntity(Customer customer, CreateCustomerRequest request) {
        if (customer == null || request == null) return;

        customer.setName(request.name());
        customer.setEmail(request.email());
    }
}
