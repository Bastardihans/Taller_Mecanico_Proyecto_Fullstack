// Archivo: dto/OrdenResponseDTO.java
package com.example.facturacionservice.dto;

// DTO espejo para simular los datos que te entregará Hans desde orden-service
public class OrdenResponseDTO {
    private Long id;
    private Long servicioId; // Qué servicio se le hizo al auto
    private Double costoRepuestos; // Cuánto gastó Hans en repuestos
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }
    public Double getCostoRepuestos() { return costoRepuestos; }
    public void setCostoRepuestos(Double costoRepuestos) { this.costoRepuestos = costoRepuestos; }
}