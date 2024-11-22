package com.youcode.citronix.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Citronix Farm Management API",
        version = "1.0",
        description = "API for managing farms, fields, and tree capacity",
        contact = @Contact(
            name = "Citronix Support",
            email = "support@citronix.com",
            url = "https://citronix.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Development Server"
        )
    }
)
public class SwaggerConfig {
    // Configuration is handled through annotations
}
