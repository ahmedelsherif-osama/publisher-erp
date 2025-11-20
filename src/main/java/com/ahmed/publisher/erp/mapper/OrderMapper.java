package com.ahmed.publisher.erp.mapper;

import com.ahmed.publisher.erp.dto.OrderDto;
import com.ahmed.publisher.erp.dto.OrderItemDto;
import com.ahmed.publisher.erp.entity.Order;
import com.ahmed.publisher.erp.entity.OrderItem;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(OrderMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public static OrderItemDto toDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity()
        );
    }
}
