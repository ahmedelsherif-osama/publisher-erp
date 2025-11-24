package com.ahmed.publisher.erp.supplier;

import com.ahmed.publisher.erp.supplier.dto.CreateSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.PatchSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.SupplierDto;
import com.ahmed.publisher.erp.supplier.dto.UpdateSupplierRequest;

import java.util.List;
import java.util.UUID;

public interface SupplierService {
    SupplierDto create(CreateSupplierRequest request);
    SupplierDto findById(UUID id);
    List<SupplierDto> findAll();
    SupplierDto update(UUID id, UpdateSupplierRequest updated);
    SupplierDto patch(UUID id, PatchSupplierRequest patched);
    void delete(UUID id);
}
