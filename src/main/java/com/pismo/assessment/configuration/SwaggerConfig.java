package com.pismo.assessment.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration for the Pismo Transaction Service API.
 * This configuration sets up the OpenAPI documentation for the API.
 *
 * @author shashi
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pismo Transaction Service API",
                version = "1.0.0",
                description = "API for managing financial transactions"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development Server")
        }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Pismo Transaction Service")
                        .version("1.0.0")
                        .description("Pismo Transaction Service for managing financial transactions")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
