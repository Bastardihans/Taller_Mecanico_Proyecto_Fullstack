
package com.example.ordentrabajo.ordentrabajoservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrdenTrabajoResponseDTO {

    private Long id;

    private Long vehiculoId;

    private Long mecanicoId;

    private LocalDateTime fechaIngreso;

    private String descripcionFalla;

    private String estado;
}

