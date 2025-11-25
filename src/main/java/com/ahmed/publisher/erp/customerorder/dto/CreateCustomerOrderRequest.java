package com.ahmed.publisher.erp.customerorder.dto;

import java.util.List;

public record CreateCustomerOrderRequest(
        List<CreateCustomerOrderItemRequest> items
) {}
