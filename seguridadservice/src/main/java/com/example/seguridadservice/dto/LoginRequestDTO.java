// Archivo: dto/LoginRequestDTO.java
package com.example.seguridadservice.dto;

import jakarta.validation.constraints.*; // Habilita las validaciones de la PPT 2.3.1

public class LoginRequestDTO {

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido") // 1. Valida automáticamente la estructura de un email
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    // Getters y Setters tradicionales para el transporte HTTP
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}