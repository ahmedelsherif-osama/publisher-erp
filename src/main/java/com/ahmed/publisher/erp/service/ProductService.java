package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.dto.CreateProductRequest;
import com.ahmed.publisher.erp.dto.PatchProductRequest;
import com.ahmed.publisher.erp.dto.ProductDto;
import com.ahmed.publisher.erp.dto.UpdateProductRequest;
import com.ahmed.publisher.erp.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto create(CreateProductRequest request);
    ProductDto findById(UUID id);
    List<ProductDto> findAll();
    ProductDto update(UUID id, UpdateProductRequest updated);
    ProductDto patch(UUID id, PatchProductRequest updated);
    void delete(UUID id);
}
