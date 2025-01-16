package com.projects.CarbonCalculator.model.enums;

public enum TransportMethod {
	DIESEL_CAR_SMALL ("diesel-car-small", 142.0),
	PETROL_CAR_SMALL ("petrol-car-small", 154.0),
	PLUGIN_HYBRID_CAR_SMALL ("plugin-hybrid-car-small", 73.0),
	ELECTRIC_CAR_SMALL ("electric-car-small", 50.0),
    DIESEL_CAR_MEDIUM("diesel-car-medium", 171.0),
    PETROL_CAR_MEDIUM("petrol-car-medium", 192.0),
    PLUGIN_HYBRID_CAR_MEDIUM("plugin-hybrid-car-medium", 110.0),
    ELECTRIC_CAR_MEDIUM("electric-car-medium", 58.0),
    DIESEL_CAR_LARGE("diesel-car-large", 209.0),
    PETROL_CAR_LARGE("petrol-car-large", 282.0),
    PLUGIN_HYBRID_CAR_LARGE("plugin-hybrid-car-large", 126.0),
    ELECTRIC_CAR_LARGE("electric-car-large", 73.0),
	BUS_DEFAULT("bus-default", 27.0),
	TRAIN_DEFAULT("train-default", 6.0);

    private final String type;
    private final double emissionFactor;

    TransportMethod(String type, double emissionFactor) {
        this.type = type;
        this.emissionFactor = emissionFactor;
    }

    public String getType() {
        return type;
    }

    public double getEmissionFactor() {
        return emissionFactor;
    }

    public static TransportMethod getValue(String type) {
        for (TransportMethod method : TransportMethod.values()) {
            if (method.getType().equals(type)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid transportation method: " + type);
    }
}
