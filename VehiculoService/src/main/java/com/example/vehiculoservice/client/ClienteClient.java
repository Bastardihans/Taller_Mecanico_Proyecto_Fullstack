package com.example.vehiculoservice.client;

import com.example.vehiculoservice.dto.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Usamos el nombre que tu Service ya está importando (ClienteClient)
@FeignClient(name = "cliente-service", url = "${cliente.service.url}")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteResponseDTO obtenerClientePorId(@PathVariable("id") Long id);
}