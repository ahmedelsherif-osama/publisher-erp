package com.ahmed.publisher.erp.supplier;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.supplier.dto.CreateSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.PatchSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.SupplierDto;
import com.ahmed.publisher.erp.supplier.dto.UpdateSupplierRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repo;

    public SupplierServiceImpl(SupplierRepository repo){
        this.repo = repo;
    }

    @Override
    public SupplierDto create(CreateSupplierRequest request) {
        Supplier supplier = SupplierMapper.toEntity(request);
        Supplier saved = repo.save(supplier);
        return SupplierMapper.toDto(saved);
    }

    @Override
    public SupplierDto findById(UUID id) {
        Supplier supplier = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier Not Found"));

        return SupplierMapper.toDto(supplier);
    }

    @Override
    public List<SupplierDto> findAll() {
        return repo.findAll()
                .stream()
                .map(SupplierMapper::toDto)
                .toList();
    }

    @Override
    public SupplierDto update(UUID id, UpdateSupplierRequest updated) {
        Supplier existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        SupplierMapper.applyUpdate(existing, updated);
        return SupplierMapper.toDto(repo.save(existing));
    }

    @Override
    public SupplierDto patch(UUID id, PatchSupplierRequest patched) {
        Supplier existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        SupplierMapper.applyPatch(existing, patched);
        return SupplierMapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
