package com.ahmed.publisher.erp.exceptions.http;

import com.ahmed.publisher.erp.exceptions.base.ApiException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
