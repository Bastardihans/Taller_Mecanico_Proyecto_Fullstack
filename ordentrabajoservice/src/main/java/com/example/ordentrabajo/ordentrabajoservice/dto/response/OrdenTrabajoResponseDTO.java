
package com.example.ordentrabajo.ordentrabajoservice.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

