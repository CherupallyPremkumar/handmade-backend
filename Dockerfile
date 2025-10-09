# Stage 1: Build the Spring Boot jar
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and source code
COPY pom.xml .
COPY src ./src

# Build the jar (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/admin-suite-backend-1.0.0.jar app.jar

# Expose port (Cloud Run automatically detects PORT env variable)
ENV PORT=8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]