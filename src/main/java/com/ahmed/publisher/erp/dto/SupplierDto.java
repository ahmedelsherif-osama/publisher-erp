package com.ahmed.publisher.erp.dto;

import java.util.UUID;

public record SupplierDto(
        UUID id,
        String name,
        String contactEmail,
        String phone,
        String address,
        String tradeLicenseNumber
) {}
