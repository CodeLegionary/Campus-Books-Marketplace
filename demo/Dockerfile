# backend/Dockerfile

# --- Stage 1: Build the Spring Boot application ---
FROM maven:3.9.3-eclipse-temurin-17 AS builder
# Good base image for Maven and Java 17
WORKDIR /build

# IMPROVEMENT 1: Leverage Docker Layer Caching for dependencies
# Copy only the pom.xml first. If pom.xml doesn't change, Maven dependencies
# won't be re-downloaded, speeding up builds.
COPY pom.xml ./
RUN mvn dependency:go-offline # Download dependencies without building the app yet

# Copy the rest of the source code
COPY . .

# Build the application, skipping tests for a faster production build
RUN mvn clean package -DskipTests

# --- Stage 2: Create the final runtime image ---
FROM eclipse-temurin:17-jdk
# Good, lean JDK image for runtime

# IMPROVEMENT 2 (Security Best Practice): Create a non-root user
# Running applications as non-root is a security best practice.
# 'nobody' is a common choice for simple non-privileged user.
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
USER appuser

WORKDIR /app
# Copy the built JAR from the 'builder' stage to the final image
COPY --from=builder /build/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the entrypoint command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]