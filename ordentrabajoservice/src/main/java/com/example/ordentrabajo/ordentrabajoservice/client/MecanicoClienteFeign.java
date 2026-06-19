package com.example.ordentrabajo.ordentrabajoservice.client;

import com.example.ordentrabajo.ordentrabajoservice.dto.response.MecanicoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "mecanico-client",
        url = "${mecanico.service.url}"
)
public interface MecanicoClienteFeign {

    @GetMapping("/api/v1/mecanicos/{id}")
    MecanicoResponseDTO obtenerMecanicoPorId(
            @PathVariable("id") Long id);
}

