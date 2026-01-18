package com.ahmed.publisher.erp.order.mapper;

import com.ahmed.publisher.erp.order.dto.OrderItemResponse;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import com.ahmed.publisher.erp.order.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getItems().stream()
                        .map(i -> new OrderItemResponse(
                                        i.getPublication().getId(),
                                        i.getQuantity()
                                )
                        ).collect(Collectors.toList()),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
