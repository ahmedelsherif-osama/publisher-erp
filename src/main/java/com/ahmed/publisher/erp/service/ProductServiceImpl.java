package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.dto.CreateProductRequest;
import com.ahmed.publisher.erp.dto.PatchProductRequest;
import com.ahmed.publisher.erp.dto.ProductDto;
import com.ahmed.publisher.erp.dto.UpdateProductRequest;
import com.ahmed.publisher.erp.entity.Product;
import com.ahmed.publisher.erp.mapper.ProductMapper;
import com.ahmed.publisher.erp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo){
        this.repo=repo;
    }

    @Override
    public ProductDto create(CreateProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        Product saved = repo.save(product);
        return ProductMapper.toDto(saved);
    }

    @Override
    public ProductDto findById(UUID id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        return ProductMapper.toDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return (repo.findAll()).stream().map(ProductMapper::toDto).toList();
    }


    @Override
    public ProductDto update(UUID id, UpdateProductRequest updated) {
        Product existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductMapper.applyUpdate(existing,updated);
        return ProductMapper.toDto(repo.save(existing));
    }

    @Override
    public ProductDto patch(UUID id, PatchProductRequest patched) {
        Product existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductMapper.applyPatch(existing, patched);
        return ProductMapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
