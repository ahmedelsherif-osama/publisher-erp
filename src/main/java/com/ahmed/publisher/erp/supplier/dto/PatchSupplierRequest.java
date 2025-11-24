package com.ahmed.publisher.erp.supplier.dto;

public record PatchSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
