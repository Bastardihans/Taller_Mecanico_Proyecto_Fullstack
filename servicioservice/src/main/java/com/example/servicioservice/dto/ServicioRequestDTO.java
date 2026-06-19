// Archivo: dto/ServicioRequestDTO.java
package com.example.servicioservice.dto;

import jakarta.validation.constraints.NotBlank; // Importa las anotaciones de validación de la PPT
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServicioRequestDTO {

    @NotBlank(message = "El nombre del servicio no puede estar vacío") // 1. Valida que no venga vacío ni con espacios
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio base es obligatorio") // 2. Para números usamos @NotNull (no @NotBlank)
    @Positive(message = "El precio base debe ser un número mayor a cero") // 3. ¡Evita precios negativos en el taller!
    private Double precioBase;

    // Getters y Setters tradicionales para asegurar el entendimiento del flujo
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }
}