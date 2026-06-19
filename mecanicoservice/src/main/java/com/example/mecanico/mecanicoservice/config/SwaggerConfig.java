package com.example.mecanico.mecanicoservice.config; // Adapta el paquete a tu proyecto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Mecánicos del taller mecánico")
                        .version("1.0")
                        .description("Gestión de mecánicos, especialidades, disponibilidad y CRUD completo."));
    }
}