// Archivo: src/main/java/com/example/agendaservice/dto/ClienteResponseDTO.java
package com.example.agendaservice.dto;

public class ClienteResponseDTO {
    private Long id;       // ID del cliente en la BD de Hans
    private String nombre; // Nombre del cliente
    private String correo; // Correo electrónico del cliente

    // Getters y Setters standard para transportar los datos que nos entregue Hans
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}