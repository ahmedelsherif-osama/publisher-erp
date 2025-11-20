package com.ahmed.publisher.erp.dto;

public record CreateSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
