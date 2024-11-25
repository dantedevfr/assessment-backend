package com.dantedev.assessment_backend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        try {
            System.out.println("Converting JsonNode to String with explicit cast: " + attribute);
            return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error serializing JSON to String", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            // Convierte la cadena JSON de la base de datos en JsonNode
            return dbData != null ? objectMapper.readTree(dbData) : objectMapper.createObjectNode(); // Objeto JSON vacío si es null
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializando String a JsonNode", e);
        }
    }
}
