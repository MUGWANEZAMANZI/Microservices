package com.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleDownstreamError(HttpServerErrorException ex) {
        String body = ex.getResponseBodyAsString();
        String message = body.isBlank() ? ex.getMessage() : body;
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", ex.getStatusCode().value(),
                "error", "Downstream Service Error",
                "message", message
        ));
    }
}
