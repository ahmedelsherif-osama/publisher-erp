package com.ahmed.publisher.erp.supplier;

import com.ahmed.publisher.erp.supplier.dto.CreateSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.PatchSupplierRequest;
import com.ahmed.publisher.erp.supplier.dto.SupplierDto;
import com.ahmed.publisher.erp.supplier.dto.UpdateSupplierRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDto create(@RequestBody CreateSupplierRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public SupplierDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<SupplierDto> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public SupplierDto update(@PathVariable UUID id, @RequestBody UpdateSupplierRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public SupplierDto patch(@PathVariable UUID id, @RequestBody PatchSupplierRequest request) {
        return service.patch(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
