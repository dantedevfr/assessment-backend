package com.dantedev.assessment_backend.exceptions;

/**
 * Exception thrown when a request is invalid.
 */
public class BadRequestException extends RuntimeException {

    private final String errorCode;

    public BadRequestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}