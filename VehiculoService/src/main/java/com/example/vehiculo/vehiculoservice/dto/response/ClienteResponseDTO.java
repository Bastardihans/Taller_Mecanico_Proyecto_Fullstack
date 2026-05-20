
package com.example.vehiculo.vehiculoservice.dto.response;

import lombok.Builder;
import lombok.Data;



@Data
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

