package com.ahmed.publisher.erp.exceptions.notfound;


import com.ahmed.publisher.erp.exceptions.ResourceNotFoundException;

import java.util.UUID;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(UUID id) {
        super("Product with id " + id + " not found");
    }
}

