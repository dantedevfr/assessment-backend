package com.dantedev.assessment_backend.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FilterHelper {

    /**
     * Valida y transforma filtros dinámicos basados en el tipo de dato.
     *
     * @param filters Los filtros recibidos en el request.
     * @param validFields Un mapa con los nombres de los campos y sus tipos esperados.
     * @return Un mapa con filtros válidos y transformados.
     */
    public static Map<String, Object> validateAndParseFilters(Map<String, String> filters, Map<String, Class<?>> validFields) {
        Map<String, Object> validFilters = new HashMap<>();

        filters.forEach((key, value) -> {
            if (value != null && validFields.containsKey(key)) {
                Class<?> fieldType = validFields.get(key);

                try {
                    if (fieldType == String.class) {
                        validFilters.put(key, value);
                    } else if (fieldType == Long.class) {
                        validFilters.put(key, Long.parseLong(value));
                    } else if (fieldType == Integer.class) {
                        validFilters.put(key, Integer.parseInt(value));
                    } else if (fieldType == LocalDateTime.class) {
                        validFilters.put(key, LocalDateTime.parse(value));
                    } else {
                        throw new IllegalArgumentException("Unsupported filter type for field: " + key);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid value for field '" + key + "': " + value);
                }
            }
        });

        return validFilters;
    }
}