package com.example.mecanico.mecanicoservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class MecanicoResponseDTO {


    private Long id;

    private String rut;

    private String nombre;

    private String especialidad;

    private String telefono;

    private boolean disponible;
}
