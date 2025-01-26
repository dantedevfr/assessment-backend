package com.dantedev.assessment_backend.exceptions;

/**
 * Centralized definition of custom error codes.
 */
public class ErrorCodes {
    // General error codes
    public static final String ERROR_INTERNAL_SERVER = "ERROR_INTERNAL_SERVER";

    // Resource not found
    public static final String ERROR_RESOURCE_NOT_FOUND_LEVEL = "ERROR_RESOURCE_NOT_FOUND_LEVEL";

    // Invalid requests
    public static final String ERROR_INVALID_REQUEST_LEVEL_DUPLICATE_NAME = "ERROR_INVALID_REQUEST_LEVEL_DUPLICATE_NAME";

    private ErrorCodes() {
        // Prevent instantiation
    }
}