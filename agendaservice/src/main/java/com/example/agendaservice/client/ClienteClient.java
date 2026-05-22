// Archivo: src/main/java/com/example/agendaservice/client/ClienteClient.java
package com.example.agendaservice.client;

import org.springframework.cloud.openfeign.FeignClient; // Importamos el DTO de transporte
import org.springframework.web.bind.annotation.GetMapping;   // Importamos la anotación de Feign
import org.springframework.web.bind.annotation.PathVariable; // Importamos el mapeo GET

import com.example.agendaservice.dto.ClienteResponseDTO;// Importamos la variable de ruta

@FeignClient(
    name = "cliente-service",        // 1. Nombre único del cliente Feign internamente en Spring
    url = "${cliente.service.url}"   // 2. ¡CLAVE! Lee la URL de la variable de entorno de Docker que configuramos antes
)
public interface ClienteClient {

    // 3. Le decimos a Feign: "Haz un GET a la ruta /api/v1/clientes/{id} del servicio de Hans"
    @GetMapping("/api/v1/clientes/{id}")
    ClienteResponseDTO obtenerClientePorId(
        @PathVariable("id") Long id // 4. Inyecta dinámicamente el ID que le pasemos en la URL
    );
}