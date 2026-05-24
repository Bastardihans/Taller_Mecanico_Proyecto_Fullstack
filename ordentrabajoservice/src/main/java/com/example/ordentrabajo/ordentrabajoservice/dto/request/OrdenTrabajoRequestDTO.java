
package com.example.ordentrabajo.ordentrabajoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrdenTrabajoRequestDTO {

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "El mecánico es obligatorio")
    private Long mecanicoId;

    @NotNull(message = "El servicio es obligatorio")
    private Long servicioId;

    @NotNull(message = "El costo de los repuestos es obligatorio")
    @PositiveOrZero(message = "El costo de los repuestos no puede ser negativo")
    private Double costoRepuestos;

    @NotBlank(message = "La descripción de la falla es obligatoria")
    private String descripcionFalla;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}

