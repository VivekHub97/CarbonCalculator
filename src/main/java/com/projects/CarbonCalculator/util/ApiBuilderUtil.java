package com.projects.CarbonCalculator.util;

public class ApiBuilderUtil {
	
    public static String matrixPayload(String startCoordinates, String endCoordinates) {
        return String.format("{\"locations\":[" +startCoordinates+ "," +endCoordinates+ "],\"metrics\":[\"distance\"]}");
    }

}
