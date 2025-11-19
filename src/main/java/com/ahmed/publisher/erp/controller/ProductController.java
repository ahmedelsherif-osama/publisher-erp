package com.ahmed.publisher.erp.controller;

import com.ahmed.publisher.erp.entity.Product;
import com.ahmed.publisher.erp.repository.ProductRepository;
import com.ahmed.publisher.erp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service=service;
    }

    @GetMapping
    public List<Product> getAll(){
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return service.saveProduct(product);
    }

}
