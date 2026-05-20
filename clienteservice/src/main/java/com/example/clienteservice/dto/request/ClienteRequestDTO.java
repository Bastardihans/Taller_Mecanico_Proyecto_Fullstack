
package com.example.clienteservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;

    private String telefono;

    @Email(message = "Correo inválido")
    private String correo;

    private String direccion;
}

