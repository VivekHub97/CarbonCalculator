package com.projects.CarbonCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CarbonCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarbonCalculatorApplication.class, args);
	}

}
