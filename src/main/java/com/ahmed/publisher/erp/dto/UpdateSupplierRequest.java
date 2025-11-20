package com.ahmed.publisher.erp.dto;

public record UpdateSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
