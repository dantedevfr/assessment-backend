package com.dantedev.assessment_backend.exceptions;

/**
 * Centralized definition of error codes.
 */
public class ErrorCodes {
    public static final String ERROR_USER_WITH_SAME_AGE = "ERROR_USER_WITH_SAME_AGE";
    public static final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    public static final String ERROR_RESOURCE_NOT_FOUND = "ERROR_RESOURCE_NOT_FOUND";
    public static final String ERROR_INVALID_REQUEST = "ERROR_INVALID_REQUEST";
    public static final String ERROR_INTERNAL_SERVER = "ERROR_INTERNAL_SERVER";

    private ErrorCodes() {
        // Prevent instantiation
    }
}