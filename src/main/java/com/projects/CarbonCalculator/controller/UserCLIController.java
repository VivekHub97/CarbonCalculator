package com.projects.CarbonCalculator.controller;

import org.springframework.stereotype.Component;

import com.projects.CarbonCalculator.service.CalculatorService;

import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserCLIController implements CommandLineRunner {

    private CalculatorService calculatorService;
    
    public UserCLIController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CLI Input Arguments: " + Arrays.toString(args));

        Map<String, String> params = parseArgs(args);

        String start = params.get("start");
        String end = params.get("end");
        String transportationMethod = params.get("transportation-method");

        if (start == null || end == null || transportationMethod == null) {
            System.out.println("Invalid input. Please provide --start, --end, and --transportation-method parameters.");
            return;
        }

        if (start != null && end != null && transportationMethod != null) {
            System.out.println("Start: " + start);
            System.out.println("End: " + end);
            System.out.println("Transportation Method: " + transportationMethod);

            double co2Emission = calculatorService.calculateCo2(start, end, transportationMethod);
            System.out.printf("your trip caused " + (Math.round(co2Emission * 10.0))/10.0 + "kg of CO2-equivalent");
        } else {
            System.out.println("Invalid input. Please provide all required parameters.");
        }
    }
    
    private Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String[] split = args[i].split("=", 2);
                if (split.length == 2) {
                    // Handle "--key=value" format
                    params.put(split[0].substring(2), split[1]);
                } else if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    // Handle "--key value" format
                    params.put(args[i].substring(2), args[i + 1]);
                    i++; // Skip the next argument as it's the value
                }
            }
        }
        return params;
    }
}
