package com.ahmed.publisher.erp.exceptions.http;

import com.ahmed.publisher.erp.exceptions.base.ApiException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}