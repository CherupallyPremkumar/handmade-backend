# Stage 1: Build the Spring Boot jar with Maven
FROM maven:3.9.0-amazoncorretto-17 AS build
WORKDIR /app

# Copy pom and source code
COPY pom.xml .
COPY src ./src

# Build the jar (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Runtime image with Corretto 17
FROM amazoncorretto:17
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/admin-suite-backend-1.0.0.jar app.jar

# Cloud Run uses PORT environment variable
ENV PORT=8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]