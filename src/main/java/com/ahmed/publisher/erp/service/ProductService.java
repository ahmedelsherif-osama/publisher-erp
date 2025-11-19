package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.entity.Product;
import com.ahmed.publisher.erp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo){
        this.repo=repo;
    }

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getById(UUID id){
        return repo.findById(id).orElse(null);
    }

    public Product saveProduct(Product product){
        return repo.save(product);
    }
}
