package com.example.agendaservice.dto;

import java.time.LocalDateTime;

public class AgendaResponseDTO {
    private Long id;
    private Long clienteId;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;

    // Constructor útil para transformar rápido
    public AgendaResponseDTO(Long id, Long clienteId, LocalDateTime fechaHora, String motivo, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
}