package com.example.vehiculo.vehiculoservice.client;

import com.example.vehiculo.vehiculoservice.dto.response.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente-client",
        url = "${cliente.service.url}"
)
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteResponseDTO obtenerClientePorId(
            @PathVariable("id") Long id
    );
}