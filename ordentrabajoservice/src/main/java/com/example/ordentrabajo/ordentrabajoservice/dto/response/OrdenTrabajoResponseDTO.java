
package com.example.ordentrabajo.ordentrabajoservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTrabajoResponseDTO {

    private Long id;

    private Long vehiculoId;

    private Long mecanicoId;

    private Long servicioId;

    private Double costoRepuestos;

    private LocalDateTime fechaIngreso;

    private String descripcionFalla;

    private String estado;
}

