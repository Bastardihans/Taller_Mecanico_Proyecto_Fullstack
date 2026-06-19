// Archivo: dto/ServicioResponseDTO.java
package com.example.servicioservice.dto;

// Este DTO sirve para enviar los datos hacia afuera de forma segura y limpia
public class ServicioResponseDTO {
    private Long id; // Incluye el ID ya generado por la base de datos
    private String nombre;
    private String descripcion;
    private Double precioBase;

    // Constructor explícito para transformar rápido los datos
    public ServicioResponseDTO(Long id, String nombre, String descripcion, Double precioBase) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

    // Getters necesarios para que Spring pueda transformarlo a formato JSON automáticamente
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecioBase() { return precioBase; }
}