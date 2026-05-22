// Archivo: dto/ServicioResponseDTO.java
package com.example.facturacionservice.dto;

// DTO espejo para capturar el precio base de la mano de obra desde tu propio servicio-service
public class ServicioResponseDTO {
    private Double precioBase;
    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }
}