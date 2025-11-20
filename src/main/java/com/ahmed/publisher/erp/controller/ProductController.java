package com.ahmed.publisher.erp.controller;

import com.ahmed.publisher.erp.dto.CreateProductRequest;
import com.ahmed.publisher.erp.dto.PatchProductRequest;
import com.ahmed.publisher.erp.dto.ProductDto;
import com.ahmed.publisher.erp.dto.UpdateProductRequest;

import com.ahmed.publisher.erp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable UUID id){

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody CreateProductRequest request){
        ProductDto created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody UUID id,@RequestBody UpdateProductRequest request){
        return ResponseEntity.ok(service.update(id,request));
    }

    @PatchMapping
    public ResponseEntity<ProductDto> updatePartially(@RequestBody UUID id,@RequestBody PatchProductRequest request){
        return ResponseEntity.ok(service.patch(id,request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }

}
