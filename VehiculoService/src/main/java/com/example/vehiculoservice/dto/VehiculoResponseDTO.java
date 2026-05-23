package com.example.vehiculoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {
    private Long id;
    private Long clienteId;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Boolean enReparacion;
}