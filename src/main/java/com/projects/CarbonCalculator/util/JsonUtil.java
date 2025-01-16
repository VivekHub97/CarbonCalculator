package com.projects.CarbonCalculator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Parse JSON string to an object of the specified class (Geocode or Matrix)
    public static <T> T parseJson(String json, Class<T> modelClass) {
        try {
            return objectMapper.readValue(json, modelClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
