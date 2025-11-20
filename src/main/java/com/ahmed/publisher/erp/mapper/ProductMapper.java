package com.ahmed.publisher.erp.mapper;

import com.ahmed.publisher.erp.dto.*;
import com.ahmed.publisher.erp.entity.Product;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest req) {
        Product p = new Product();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStockQuantity(req.initialStock());
        return p;
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    public static void applyUpdate(Product existing, UpdateProductRequest req) {
        existing.setName(req.name());
        existing.setDescription(req.description());
        existing.setPrice(req.price());
    }

    public static void applyPatch(Product existing, PatchProductRequest req) {
        if (req.name() != null) existing.setName(req.name());
        if (req.description() != null) existing.setDescription(req.description());
        if (req.price() != null) existing.setPrice(req.price());
    }
}
