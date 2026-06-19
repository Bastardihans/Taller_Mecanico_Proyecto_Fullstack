package com.example.agendaservice.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AgendaRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La fecha y hora de la cita son obligatorias")
    @Future(message = "La cita debe ser programada para una fecha futura") // <- ¡Mágica validación de la PPT!
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo de la cita no puede estar vacío")
    @Size(min = 10, max = 255, message = "El motivo debe tener entre 10 y 255 caracteres")
    private String motivo;

    // Getters y Setters (o puedes usar @Data de Lombok si lo tienes instalado)
    // Para el ejemplo usaremos los Getters y Setters estándar para asegurar comprensión
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}