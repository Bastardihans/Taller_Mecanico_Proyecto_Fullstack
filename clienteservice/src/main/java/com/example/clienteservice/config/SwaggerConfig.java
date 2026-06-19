package com.example.clienteservice.config; // Adapta el paquete a tu proyecto

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
                        .title("API - Clientes del taller mecánico")
                        .version("1.0")
                        .description("Administración de clientes, consulta por ID y gestión CRUD completa."));
    }
}