package com.projects.CarbonCalculator.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openroute", url = "https://api.openrouteservice.org")
public interface ApiFeignClient {

    @GetMapping("/geocode/search")
    public String getCoordinates(
    		@RequestParam("api_key") String api_key, 
    		@RequestParam("text") String city, 
    		@RequestParam("size") int size);

    @PostMapping(value = "/v2/matrix/driving-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getDistance(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody String request);
}

