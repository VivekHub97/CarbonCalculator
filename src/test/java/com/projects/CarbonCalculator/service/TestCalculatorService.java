package com.projects.CarbonCalculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestCalculatorService {

	@Autowired
    private CalculatorService calculatorService;

    @Test
    void testValidParams() {

        String start = "Los Angeles";
        String end = "New York";
        String transportationMethod = "diesel-car-medium";

        double expectedEmission = 770.4;

        double actualEmission = calculatorService.calculateCo2(start, end, transportationMethod);
        //System.out.println(actualEmission);

        assertEquals(expectedEmission, actualEmission);
    }
    
    @Test
    void testNoPossibleRoute() {

        String start = "Berlin";
        String end = "New York";
        String transportationMethod = "diesel-car-medium";

        double expectedEmission = -1;

        double actualEmission = calculatorService.calculateCo2(start, end, transportationMethod);
        //System.out.println(actualEmission);

        assertEquals(expectedEmission, actualEmission);
    }
    
    @Test
    void testNoRealCity() {

        String start = "xyzabc";
        String end = "New York";
        String transportationMethod = "diesel-car-medium";

        double expectedEmission = -1;

        double actualEmission = calculatorService.calculateCo2(start, end, transportationMethod);
        //System.out.println(actualEmission);

        assertEquals(expectedEmission, actualEmission);
    }
}
