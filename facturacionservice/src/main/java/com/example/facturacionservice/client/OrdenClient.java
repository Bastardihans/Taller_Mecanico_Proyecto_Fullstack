// Archivo: client/OrdenClient.java
package com.example.facturacionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.facturacionservice.dto.OrdenResponseDTO;

@FeignClient(name = "orden-service", url = "${orden.service.url}") // 1. Se conecta a la URL del servicio de Hans
public interface OrdenClient {

    @GetMapping("/api/v1/ordenes/{id}") // 2. Consulta la información de la orden de trabajo de un vehículo
    OrdenResponseDTO obtenerOrdenPorId(@PathVariable("id") Long id);
}