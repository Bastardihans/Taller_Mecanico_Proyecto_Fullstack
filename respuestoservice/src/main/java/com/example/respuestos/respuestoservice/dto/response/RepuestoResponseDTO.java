package com.example.respuestos.respuestoservice.dto.response;

import lombok.Data;

@Data
public class RepuestoResponseDTO {

    private Long id;
    private String nombre;
    private Integer stock;
    private Double precioUnitario;
}
