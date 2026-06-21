
package com.example.ordentrabajo.ordentrabajoservice.client;

import com.example.ordentrabajo.ordentrabajoservice.dto.response.VehiculoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "vehiculo-service"
)
public interface VehiculoClienteFeign {

    @GetMapping("/api/v1/vehiculos/{id}")
    VehiculoResponseDTO obtenerVehiculoPorId(
            @PathVariable("id") Long id
    );
}
