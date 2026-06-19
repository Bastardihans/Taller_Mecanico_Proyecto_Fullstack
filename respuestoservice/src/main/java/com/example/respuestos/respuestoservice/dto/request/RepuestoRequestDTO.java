package com.example.respuestos.respuestoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RepuestoRequestDTO {

    @NotBlank(message = "El nombre del repuesto es obligatorio")
    private String nombre;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero(message = "El precio unitario no puede ser negativo")
    private Double precioUnitario;
}
