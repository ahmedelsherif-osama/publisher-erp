package com.ahmed.publisher.erp.exceptions.business;

import com.ahmed.publisher.erp.exceptions.base.ApiException;
import org.springframework.http.HttpStatus;

public class BusinessRuleViolationException extends ApiException {
    public BusinessRuleViolationException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}