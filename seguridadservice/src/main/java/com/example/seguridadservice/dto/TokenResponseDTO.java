// Archivo: dto/TokenResponseDTO.java
package com.example.seguridadservice.dto;

// DTO que enviamos al frontend/Postman cuando el login es correcto, entregando el pase JWT
public class TokenResponseDTO {
    private String token; // El string cifrado que el usuario guardará
    private String tipo;  // Por convención REST, siempre viaja el tipo "Bearer"

    public TokenResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
}