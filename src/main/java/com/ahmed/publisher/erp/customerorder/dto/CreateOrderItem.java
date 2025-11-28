package com.ahmed.publisher.erp.customerorder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrderItem {
    private UUID productId;
    private String sku;
    private int quantity;
}