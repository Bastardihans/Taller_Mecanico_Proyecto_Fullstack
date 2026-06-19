package com.example.vehiculoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;
}
