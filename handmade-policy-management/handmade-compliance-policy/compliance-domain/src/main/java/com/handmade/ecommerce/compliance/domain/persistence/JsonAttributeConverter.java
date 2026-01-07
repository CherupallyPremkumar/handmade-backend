package com.handmade.ecommerce.compliance.domain.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JPA converter for Map<String, Object> to JSON string.
 * Abstraction for database-specific JSON types (like JSONB).
 */
@Converter(autoApply = false)
public class JsonAttributeConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final Logger logger = LoggerFactory.getLogger(JsonAttributeConverter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Error converting map to JSON string", e);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(dbData, Map.class);
        } catch (IOException e) {
            logger.error("Error converting JSON string to map", e);
            return new HashMap<>();
        }
    }
}
