
package com.example.ordentrabajo.ordentrabajoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrdenTrabajoRequestDTO {

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "El mecánico es obligatorio")
    private Long mecanicoId;

    @NotBlank(message = "La descripción de la falla es obligatoria")
    private String descripcionFalla;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}

