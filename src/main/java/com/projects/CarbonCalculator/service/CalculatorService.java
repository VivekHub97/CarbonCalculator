package com.projects.CarbonCalculator.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.CarbonCalculator.feign.ApiFeignClient;
import com.projects.CarbonCalculator.model.Geocode;
import com.projects.CarbonCalculator.model.Matrix;
import com.projects.CarbonCalculator.model.enums.TransportMethod;
import com.projects.CarbonCalculator.util.ApiBuilderUtil;
import com.projects.CarbonCalculator.util.JsonUtil;

@Service
public class CalculatorService {
	
	// Restricting size to 1 for simplicity, gets the most relevant city
	final int NO_OF_CITIES = 1;

	private ApiFeignClient feignClient;
	
	@Value("${api.key}")
	private String apiKey;

	public CalculatorService(ApiFeignClient feignClient) {
		this.feignClient = feignClient;
	}
	
	public double calculateCo2(String start, String end, String transportMethodType) {

		double distance = fetchDistance(start, end);
		double emissionFactor = TransportMethod.getValue(transportMethodType).getEmissionFactor();

		return (distance / 1000) * (emissionFactor / 1000); //to kgs
	}

	private double fetchDistance(String start, String end) {

		try {
			String coordStartJson = feignClient.getCoordinates(apiKey, start, NO_OF_CITIES);
			String coordEndJson = feignClient.getCoordinates(apiKey, end, NO_OF_CITIES);

			Geocode startGeocode = JsonUtil.parseJson(coordStartJson, Geocode.class);
			Geocode endGeocode = JsonUtil.parseJson(coordEndJson, Geocode.class);

			// Extract coordinates
			String startCoordinates = extractCoordinates(startGeocode);
			String endCoordinates = extractCoordinates(endGeocode);

			// Building API request string
			String apiPayload = ApiBuilderUtil.matrixPayload(startCoordinates, endCoordinates);

			// Get matrix distance data
			String distanceJson = feignClient.getDistance(apiKey, apiPayload);
			Matrix matrix = JsonUtil.parseJson(distanceJson, Matrix.class);

			return extractFirstDistance(matrix);
			
		} catch (Exception e) {
			throw new RuntimeException("Error fetching distance: " + e.getMessage(), e);
		}

	}

	private String extractCoordinates(Geocode geocode) {
		if (geocode.getFeatures() == null || geocode.getFeatures().isEmpty()) {
			throw new RuntimeException("No features found in the geocode response.");
		}

		List<Double> coordinates = geocode.getFeatures().get(0).getGeometry().getCoordinates();
		return coordinates.toString();
	}

	private double extractFirstDistance(Matrix matrix) {
		if (matrix.getDistances() == null || matrix.getDistances().isEmpty()) {
			throw new RuntimeException("No distances found in the matrix response.");
		}
		return matrix.getDistances().get(0).get(1);
	}

}
