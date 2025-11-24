package com.ahmed.publisher.erp.product;

import com.ahmed.publisher.erp.product.dto.CreateProductRequest;
import com.ahmed.publisher.erp.product.dto.PatchProductRequest;
import com.ahmed.publisher.erp.product.dto.ProductDto;
import com.ahmed.publisher.erp.product.dto.UpdateProductRequest;

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
