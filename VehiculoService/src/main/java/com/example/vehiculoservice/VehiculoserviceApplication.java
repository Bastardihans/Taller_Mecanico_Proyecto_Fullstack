package com.example.vehiculoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Importación para habilitar Feign

@SpringBootApplication
@EnableFeignClients // <-- OBLIGATORIO: Permite que este servicio busque e inyecte Clientes Feign
public class VehiculoserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehiculoserviceApplication.class, args);
    }
}