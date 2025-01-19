FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=build /app/target/co2-calculator.jar co2-calculator.jar

ENV ORS_TOKEN=""

ENTRYPOINT ["java", "-jar", "co2-calculator.jar"]
