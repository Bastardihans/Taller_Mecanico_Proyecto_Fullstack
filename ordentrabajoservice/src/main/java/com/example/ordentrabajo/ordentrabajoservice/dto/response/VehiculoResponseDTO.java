package com.example.ordentrabajo.ordentrabajoservice.dto.response;

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
    private int anio;
    private boolean enReparacion;
}