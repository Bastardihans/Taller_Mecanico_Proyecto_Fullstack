package com.example.vehiculo.vehiculoservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehiculoRequestDTO {

    private Long clienteId;
    
    @NotBlank(message = "Patente es obligatoria")
    private String patente;

    @NotBlank(message = "Marca es obligatoria")
    private String marca;

    @NotBlank(message = "Modelo es obligatorio")
    private String modelo;

    @Min(value = 1950, message = "El anio debe ser de 1950 en adelante")
    private int anio;

    private boolean enReparacion;

}
