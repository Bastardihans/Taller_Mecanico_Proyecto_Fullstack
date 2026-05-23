package com.example.vehiculoservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Usamos el nombre que tu Service ya está importando (ClienteClient)
@FeignClient(name = "cliente-service", url = "${CLIENTE_SERVICE_URL:http://cliente-service:8081}")
public interface ClienteClient {

    // Este es el método que tu VehiculoService llama en la línea 33
    @GetMapping("/api/clientes/{id}")
    Object obtenerClientePorId(@PathVariable("id") Long id); 
}