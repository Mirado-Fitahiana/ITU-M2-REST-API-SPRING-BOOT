package travel.booking.travel.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelBookingOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Travel Booking API")
                        .description("""
                                REST API for managing travel bookings.

                                ## Authentication
                                Use `POST /api/auth/login` to obtain a JWT token, then click **Authorize** and enter: `Bearer <token>`

                                ## Test credentials
                                | Role     | Email                  | Password |
                                |----------|------------------------|----------|
                                | ADMIN    | admin@travel.com       | password |
                                | CUSTOMER | customer@travel.com    | password |
                                | AGENT    | agent@travel.com       | password |
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Travel Booking Team")
                                .email("contact@travel.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token (obtained from /api/auth/login)")));
    }
}
