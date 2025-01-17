package com.projects.CarbonCalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geocode {

    @Override
	public String toString() {
		return "Geocode [features=" + features + "]";
	}

	@JsonProperty("features")
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {
        @Override
		public String toString() {
			return "Feature [geometry=" + geometry + "]";
		}

		@JsonProperty("geometry") // could have used geometry.coordinates
        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        @Override
		public String toString() {
			return "Geometry [coordinates=" + coordinates + "]";
		}

		@JsonProperty("coordinates")
        private List<Double> coordinates;

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }
}

