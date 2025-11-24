package com.ahmed.publisher.erp.supplier;

import com.ahmed.publisher.erp.supplier.dto.CreateSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.PatchSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.SupplierDto;
import com.ahmed.publisher.erp.supplier.dto.UpdateSupplierRequest;

public class SupplierMapper {

    public static Supplier toEntity(CreateSupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.name());
        supplier.setContactEmail(request.contactEmail());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());
        supplier.setTradeLicenseNumber(request.tradeLicenseNumber());
        return supplier;
    }

    public static SupplierDto toDto(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactEmail(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getTradeLicenseNumber()
        );
    }

    public static void applyUpdate(Supplier supplier, UpdateSupplierRequest updated) {
        supplier.setName(updated.name());
        supplier.setContactEmail(updated.contactEmail());
        supplier.setPhone(updated.phone());
        supplier.setAddress(updated.address());
        supplier.setTradeLicenseNumber(updated.tradeLicenseNumber());
    }

    public static void applyPatch(Supplier supplier, PatchSupplierRequest patched) {
        if (patched.name() != null)                supplier.setName(patched.name());
        if (patched.contactEmail() != null)        supplier.setContactEmail(patched.contactEmail());
        if (patched.phone() != null)               supplier.setPhone(patched.phone());
        if (patched.address() != null)             supplier.setAddress(patched.address());
        if (patched.tradeLicenseNumber() != null)  supplier.setTradeLicenseNumber(patched.tradeLicenseNumber());
    }
}
