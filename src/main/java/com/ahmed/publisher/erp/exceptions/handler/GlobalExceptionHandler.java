package com.ahmed.publisher.erp.exceptions.handler;

import com.ahmed.publisher.erp.exceptions.response.CustomErrorResponse;
import com.ahmed.publisher.erp.exceptions.base.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn(
                "Validation failed: {} | path={}",
                message,
                request.getRequestURI()
        );

        return error(HttpStatus.BAD_REQUEST, "Validation failed: " + message, request);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<CustomErrorResponse> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ) {
        log.warn(
                "API exception: status={} message={} path={}",
                ex.getStatus(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return error(ex.getStatus(), ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error(
                "Unhandled exception at path={}",
                request.getRequestURI(),
                ex
        );
        return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request
        );
    }

    private ResponseEntity<CustomErrorResponse> error(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(status).body(
                new CustomErrorResponse(
                        message,
                        LocalDateTime.now(),
                        request.getRequestURI()
                )
        );
    }
}
