package com.ahmed.publisher.erp.supplier.dto;

public record CreateSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
