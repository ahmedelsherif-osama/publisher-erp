package com.ahmed.publisher.erp.order.entity;

public enum OrderStatus {
    CREATED,    // order placed, stock reserved/deducted
    COMPLETED,  // order fulfilled (shipped / delivered)
    CANCELLED   // order cancelled before completion
}
