package com.ahmed.publisher.erp.common.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ConfigDataResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorResponse(ex.getMessage()));

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomErrorResponse("Internal Sever Error"));

    }
}
