# Pismo Transaction Service

This is a Spring Boot microservice for managing accounts and transactions.

## Prerequisites
- [Docker (with Docker Compose support)](https://www.docker.com/products/docker-desktop/)



## Running the Application (Docker Only)
> **Note:** The application requires a PostgreSQL database. It is recommended to run both the app and the database using Docker Compose.

1. Clone the repository and move into the project directory:
   ```sh
   git clone https://github.com/Shashidhar397/pismo-transaction-service.git
   cd pismo-transaction-service
   ```
2. Start all services:
   ```sh
   docker-compose up --build
   ```
3. The service will be available at `http://localhost:8080`.


## Hot Reload
By default, the application supports hot reload when run via Docker Compose. Any changes to the source code on the host will be picked up automatically by the running container (requires Spring Boot DevTools and the correct volume mounts).

## API Documentation
- Once running, access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Database
- The application uses Flyway for DB migrations. SQL scripts are in `src/main/resources/db/migration`.

## Health Check
- Spring Boot Actuator endpoints are enabled
- Health check endpoint: `http://localhost:8080/actuator/health`
