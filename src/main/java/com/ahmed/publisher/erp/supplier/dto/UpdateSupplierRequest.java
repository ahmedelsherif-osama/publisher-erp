package com.ahmed.publisher.erp.supplier.dto;

public record UpdateSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
