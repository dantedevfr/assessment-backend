package com.dantedev.assessment_backend.utils;

/**
 * Utility class to generate standardized codes for errors, warnings, and other messages.
 */
public class ErrorCodeGenerator {

    /**
     * Generates a standardized code.
     *
     * @param severity The severity of the code (e.g., ERROR, WARNING).
     * @param type     The type of the code (e.g., RESOURCE_NOT_FOUND, INVALID_REQUEST).
     * @param entity   The entity involved (e.g., LEVEL, USER).
     * @param context  Additional context or specific details (optional).
     * @return A standardized code string.
     */
    public static String generate(String severity, String type, String entity, String context) {
        StringBuilder code = new StringBuilder(severity.toUpperCase());
        code.append("_").append(type.toUpperCase());
        code.append("_").append(entity.toUpperCase());
        if (context != null && !context.isBlank()) {
            code.append("_").append(context.toUpperCase());
        }
        return code.toString();
    }
}
