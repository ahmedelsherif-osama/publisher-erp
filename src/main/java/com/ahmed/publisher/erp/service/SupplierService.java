package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.entity.Supplier;
import com.ahmed.publisher.erp.repository.SupplierRepository;

import java.util.List;
import java.util.UUID;

public class SupplierService {
    private final SupplierRepository supplierRepository;
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository=supplierRepository;
    }

    public List<Supplier> findAll(){
        return supplierRepository.findAll();
    }

    public Supplier findById(UUID id){
        return supplierRepository.findById(id).orElse(null);
    }

    public Supplier save(Supplier supplier){
        return supplierRepository.save(supplier);
    }
}
