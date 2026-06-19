package com.example.clienteservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;

    
}