# CO2 Emission Calculator

This application calculates the Co2 emissions caused when traveling between two cities using a given transportation method. It uses the OpenRouteService APIs.
The code is structured with proper separation of concerns and in a way that makes it easy to scale up and add many features. It follows proper clean coding standards and architecture.

A docker image is also available and can be pulled using the command:
``docker pull docvk1997/co2calculator``

# Features

- Calculate CO2 emissions for trips between two cities.
- Supports multiple transportation methods.
- API token is taken from an environment variable
- Includes unit tests for edge cases

# Prerequisites

1. Java Development Kit (JDK 17+) installed
2. Maven installed
3. OpenRouteService API Token: [https://openrouteservice.org/](https://openrouteservice.org/ "https://openrouteservice.org/")

# Setup

1. Download and extract the attached .zip file
2. Go to the root directory with the src folder, pom.xml file, etc
3. Set the ORS_TOKEN environment variable with your API token:

   On Windows:
   ``set ORS_TOKEN=token``

   On Linux/macOS
   ``export ORS_TOKEN=token``

# Build and Compile

To build and package the application into an executable JAR file:

``mvn clean package``

This creates a .jar file in target folder: target/co2-calculator.jar

# Run the Application

Execute the application as shown in the examples.

Examples:

``java -jar target/co2-calculator.jar --start Hamburg --end Berlin --transportation-method diesel-car-medium``

``java -jar target/co2-calculator.jar --start "Los Angeles" --end "New York" --transportation-method=diesel-car-medium``

``java -jar target/co2-calculator.jar --end "New York" --start "Los Angeles" --transportation-method=electric-car-large``

#### Below are the supported transportation methods.

`--transportation-method` in CO2e per passenger per km:
- **Small cars:**
	- `diesel-car-small` : 142g
	- `petrol-car-small` : 154g
	- `plugin-hybrid-car-small` : 73g
	- `electric-car-small` : 50g
- **Medium cars:**
	- `diesel-car-medium` : 171g
	- `petrol-car-medium` : 192g
	- `plugin-hybrid-car-medium` : 110g
	- `electric-car-medium` : 58g
- **Large cars:**
	- `diesel-car-large` : 209g
	- `petrol-car-large` : 282g
	- `plugin-hybrid-car-large` : 126g
	- `electric-car-large` : 73g
	- `bus-default` : 27g
	- `train-default` : 6g

*Modified values based on: [BEIS/Defra Greenhouse Gas Conversion Factors 2019](https://www.gov.uk/government/publications/greenhouse-gas-reporting-conversion-factors-2019 "BEIS/Defra Greenhouse Gas Conversion Factors 2019")*

# Testing

Run the unit tests using Maven:
``mvn test``

# Docker Support

Added support to run the application on any OS with Docker installed and easy integration in a CI/CD environment.

### Build Docker Image

To build a Docker image that compiles and run the application using the Dockerfile provided in the root folder:
``docker build -t co2-calculator .``

### Run Docker Container

To run the application inside a Docker container, run the following example commands with your own ORS token.

Examples:

``docker run --rm -e ORS_TOKEN=token co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium``

# Known Limitations

- The API fetches the top most relevant result (Geocode) when searching from a city name.
  In short - the 'size' parameter of /geocode/search API is hardcoded to 1.
- User Input is only via terminal.
- Test cases are limited.
