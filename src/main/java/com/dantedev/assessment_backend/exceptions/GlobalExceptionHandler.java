package com.dantedev.assessment_backend.exceptions;

import com.dantedev.assessment_backend.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Resource not found",
                null,
                ex.getErrorCode(), // Usa el código de error específico de la excepción
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Invalid request",
                null,
                ex.getErrorCode(), // Usa el código de error específico de la excepción
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Internal server error",
                null,
                ErrorCodes.ERROR_INTERNAL_SERVER, // Mantiene un código genérico para excepciones no controladas
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}