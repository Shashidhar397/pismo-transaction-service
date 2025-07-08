# Development Dockerfile for hot-reload
FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app

# Copy pom.xml and download dependencies first (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Run tests to ensure code quality before starting
RUN mvn test

# Expose port (default Spring Boot port)
EXPOSE 8080

# Run in dev mode with hot reload
ENTRYPOINT ["mvn", "spring-boot:run"]