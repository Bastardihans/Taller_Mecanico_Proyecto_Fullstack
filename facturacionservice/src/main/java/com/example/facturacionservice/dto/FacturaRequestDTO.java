// Archivo: dto/FacturaRequestDTO.java
package com.example.facturacionservice.dto;

import jakarta.validation.constraints.NotNull; // Importa Bean Validation

public class FacturaRequestDTO {

    @NotNull(message = "El ID de la orden de trabajo es obligatorio para facturar") // 1. Filtro estricto
    private Long ordenId;

    public Long getOrdenId() { return ordenId; }
    public void setOrdenId(Long ordenId) { this.ordenId = ordenId; }
}