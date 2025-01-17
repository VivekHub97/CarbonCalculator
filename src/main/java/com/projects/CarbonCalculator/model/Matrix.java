package com.projects.CarbonCalculator.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Matrix {

    @JsonProperty("distances")
    private List<List<Double>> distances;

    public List<List<Double>> getDistances() {
        return distances;
    }

    public void setDistances(List<List<Double>> distances) {
        this.distances = distances;
    }

	@Override
	public String toString() {
		return "Matrix [distances=" + distances + "]";
	}
    
}
