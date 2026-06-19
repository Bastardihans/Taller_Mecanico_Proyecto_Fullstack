package com.example.ordentrabajo.ordentrabajoservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MecanicoResponseDTO {

    private Long id;

    private String rut;

    private String nombre;

    private String especialidad;

    private String telefono;

    private boolean disponible;
}
