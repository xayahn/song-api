# ==========================================
# Stage 1: Build the application using Maven
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the JAR file (bypasses your local mvnw entirely)
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Run the application
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Render assigns a PORT dynamically, but 8080 is the Spring Boot default
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]