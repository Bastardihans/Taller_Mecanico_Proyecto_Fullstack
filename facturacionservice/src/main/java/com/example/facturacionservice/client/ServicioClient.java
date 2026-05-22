// Archivo: client/ServicioClient.java
package com.example.facturacionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.facturacionservice.dto.ServicioResponseDTO;

@FeignClient(name = "servicio-service", url = "${servicio.service.url}") // 1. Se conecta a la URL de tu catálogo de servicios
public interface ServicioClient {

    @GetMapping("/api/v1/servicios/{id}") // 2. Llama al GET que programamos en la lección anterior
    ServicioResponseDTO obtenerServicioPorId(@PathVariable("id") Long id);
}