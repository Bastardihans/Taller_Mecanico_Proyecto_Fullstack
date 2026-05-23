package com.example.vehiculoservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Controla errores de validación de DTOs (@NotBlank, @Min, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Error de validación");

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "BAD REQUEST", mensaje, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Controla cuando un vehículo solicitado no existe
    @ExceptionHandler(VehiculoNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(VehiculoNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "NOT FOUND", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Atrapalotodo para errores generales (Imprime la traza completa en la terminal de Docker)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // <-- CRUCIAL: Muestra el error real en la terminal negra de Docker

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "INTERNAL SERVER ERROR", 
                ex.getMessage() != null ? ex.getMessage() : "Ocurrió una falla inesperada en el servidor.", 
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}