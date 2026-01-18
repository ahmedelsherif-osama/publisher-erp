package com.ahmed.publisher.erp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddressDto(
    UUID id, // optional for PATCH, required for update
    Integer buildingNumber,
    @NotBlank String street,
    @NotBlank String area,
    @NotBlank String city,
    @NotBlank String countryCode,
    String postCode,
    String extraDetails,
    @NotNull
    boolean isMainAddress
) {}
