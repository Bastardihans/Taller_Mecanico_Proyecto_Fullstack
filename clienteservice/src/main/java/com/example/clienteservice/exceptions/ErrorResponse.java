package com.example.clienteservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO estándar para respuestas de error en JSON
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime fecha;
    private int status;
    private String error;
    private String mensaje;
    private String ruta;
    
}