package com.dantedev.assessment_backend.utils;

/**
 * Utility class to generate standardized error codes.
 */
public class ErrorCodeGenerator {

    /**
     * Generates a standardized error code.
     *
     * @param type   The type of the error (e.g., RESOURCE_NOT_FOUND, INVALID_REQUEST).
     * @param entity The entity involved (e.g., LEVEL, USER).
     * @param context Additional context or specific details (optional).
     * @return A standardized error code string.
     */
    public static String generate(String type, String entity, String context) {
        StringBuilder code = new StringBuilder("ERROR_");
        code.append(type.toUpperCase());
        code.append("_").append(entity.toUpperCase());
        if (context != null && !context.isBlank()) {
            code.append("_").append(context.toUpperCase());
        }
        return code.toString();
    }
}
