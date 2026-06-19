// Archivo: dto/NotificacionRequestDTO.java
package com.example.notificacionservice.dto;

import jakarta.validation.constraints.NotBlank; // Importa las anotaciones de validación JSR 380 (Bean Validation)
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NotificacionRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio") // 1. Valida que el ID del destinatario no sea nulo
    private Long clienteId;

    @NotBlank(message = "El medio de envío no puede estar vacío") // 2. Comprueba que el canal tenga texto válido
    private String medio;

    @NotBlank(message = "El cuerpo del mensaje no puede estar vacío") // 3. Evita el envío de notificaciones en blanco
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres") // 4. Restringe la longitud del texto
    private String mensaje;

    // Getters y Setters tradicionales para el correcto mapeo de los datos enviados por HTTP
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getMedio() { return medio; }
    public void setMedio(String medio) { this.medio = medio; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}