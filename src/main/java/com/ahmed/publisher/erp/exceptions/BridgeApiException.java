package com.ahmed.publisher.erp.exceptions;

import org.springframework.http.HttpStatus;

public class BridgeApiException extends ApiException {

    public BridgeApiException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public BridgeApiException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
        initCause(cause);
    }
}
