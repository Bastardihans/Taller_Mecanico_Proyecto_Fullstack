package com.example.mecanico.mecanicoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data

public class MecanicoRequestDTO {
    
    @NotBlank(message = "Rut obligatorio")
    private String rut;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Especialidad obligatoria")
    private String especialidad;

    private String telefono;

    private boolean disponible;

}
