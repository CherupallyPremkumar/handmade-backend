package com.handmade.ecommerce.platform.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for Platform API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI platformOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Platform Management API")
                .description("REST API for marketplace platform governance and management operations")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Platform Team")
                    .email("platform@handmade-ecommerce.com"))
                .license(new License()
                    .name("Proprietary")
                    .url("https://handmade-ecommerce.com/license")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Development server"),
                new Server()
                    .url("https://api.handmade-ecommerce.com")
                    .description("Production server")
            ));
    }
}
