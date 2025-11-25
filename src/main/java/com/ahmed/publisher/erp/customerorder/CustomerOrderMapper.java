package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderItemDto;

import java.util.stream.Collectors;

public class CustomerOrderMapper {

    public static CustomerOrderDto toDto(CustomerOrder order) {
        return new CustomerOrderDto(
                order.getId(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(CustomerOrderMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public static CustomerOrderItemDto toDto(CustomerOrderItem item) {
        return new CustomerOrderItemDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity()
        );
    }
}
