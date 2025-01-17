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
		if (distance == -1) {
			return -1;
		}
		double emissionFactor = TransportMethod.getValue(transportMethodType).getEmissionFactor();

		return (distance / 1000) * (emissionFactor / 1000); // to kgs
	}

	private double fetchDistance(String start, String end) {

		String coordStartJson;
		String coordEndJson;

		try {
			coordStartJson = feignClient.getCoordinates(apiKey, start, NO_OF_CITIES);
			coordEndJson = feignClient.getCoordinates(apiKey, end, NO_OF_CITIES);
		} catch (Exception e) {
			System.err.println("Response from x API: " + e.getMessage());
			System.out.println("Error fetching from the API");
			return -1;
		}

		Geocode startGeocode = JsonUtil.parseJson(coordStartJson, Geocode.class);
		Geocode endGeocode = JsonUtil.parseJson(coordEndJson, Geocode.class);
		System.out.println("Start Geocode: " + startGeocode.toString());

		// Extract coordinates
		String startCoordinates = extractCoordinates(startGeocode);
		String endCoordinates = extractCoordinates(endGeocode);
		if (startCoordinates == null || endCoordinates == null) {
			return -1;
		}

		// Building API request string
		String apiPayload = ApiBuilderUtil.matrixPayload(startCoordinates, endCoordinates);

		String distanceJson;
		try {
			// Get matrix distance data
			distanceJson = feignClient.getDistance(apiKey, apiPayload);
		} catch (Exception e) {
			System.err.println("Response from API: " + e.getMessage());
			System.out.println("Error fetching from the API");
			return -1;
		}
		Matrix matrix = JsonUtil.parseJson(distanceJson, Matrix.class);
		// System.out.println("Matrix: " + matrix.toString());

		return extractFirstDistance(matrix);

	}

	private String extractCoordinates(Geocode geocode) {
		if (geocode.getFeatures() == null || geocode.getFeatures().isEmpty()) {
			System.out.println("Coordinates not found for the given city.");
			return null;
		}

		List<Double> coordinates = geocode.getFeatures().get(0).getGeometry().getCoordinates();
		return coordinates.toString();
	}

	private double extractFirstDistance(Matrix matrix) {
		if (matrix.getDistances().toString().contains("null") || matrix.getDistances().isEmpty()) {
			System.out.println("Route not possible between the given cities.");
			return -1;
		}
		return matrix.getDistances().get(0).get(1);
	}

}
