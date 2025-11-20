package com.ahmed.publisher.erp.dto;

public record PatchSupplierRequest(
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
