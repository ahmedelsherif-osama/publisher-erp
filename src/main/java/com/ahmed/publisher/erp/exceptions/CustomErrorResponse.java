package com.ahmed.publisher.erp.exceptions;

import java.time.LocalDateTime;

public record CustomErrorResponse(String message, LocalDateTime timestamp, String path) {
}
