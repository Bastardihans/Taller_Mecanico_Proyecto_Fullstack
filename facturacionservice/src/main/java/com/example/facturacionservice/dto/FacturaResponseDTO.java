// Archivo: dto/FacturaResponseDTO.java
package com.example.facturacionservice.dto;

import java.time.LocalDateTime;

public class FacturaResponseDTO {
    private Long id;
    private Long ordenId;
    private Double montoTotal;
    private String estadoPago;
    private LocalDateTime fechaEmision;

    public FacturaResponseDTO(Long id, Long ordenId, Double montoTotal, String estadoPago, LocalDateTime fechaEmision) {
        this.id = id;
        this.ordenId = ordenId;
        this.montoTotal = montoTotal;
        this.estadoPago = estadoPago;
        this.fechaEmision = fechaEmision;
    }

    // Getters para serializar a JSON
    public Long getId() { return id; }
    public Long getOrdenId() { return ordenId; }
    public Double getMontoTotal() { return montoTotal; }
    public String getEstadoPago() { return estadoPago; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
}