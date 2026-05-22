// Archivo: dto/NotificacionResponseDTO.java
package com.example.notificacionservice.dto;

import java.time.LocalDateTime;

// DTO de salida estructurado para responder al exterior de forma controlada sin exponer la entidad directa
public class NotificacionResponseDTO {
    private Long id;
    private Long clienteId;
    private String medio;
    private String mensaje;
    private LocalDateTime fechaEnvio; // Informa al cliente el momento exacto del registro

    // Constructor explícito utilizado para la conversión manual de datos en el Service
    public NotificacionResponseDTO(Long id, Long clienteId, String medio, String mensaje, LocalDateTime fechaEnvio) {
        this.id = id;
        this.clienteId = clienteId;
        this.medio = medio;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
    }

    // Getters públicos para permitir que la biblioteca Jackson serialice el objeto de Java a formato JSON
    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getMedio() { return medio; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}