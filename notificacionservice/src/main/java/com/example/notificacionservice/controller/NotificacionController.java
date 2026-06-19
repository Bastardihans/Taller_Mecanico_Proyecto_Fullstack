package com.example.notificacionservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Activa la verificación de las anotaciones del Bean Validation en el DTO
import org.springframework.web.bind.annotation.RequestMapping; // Importa los códigos de estado HTTP semánticos
import org.springframework.web.bind.annotation.RestController; // Importa el contenedor de respuestas de la PPT

import com.example.notificacionservice.dto.NotificacionRequestDTO; // Importa los mapeos de las solicitudes web REST
import com.example.notificacionservice.dto.NotificacionResponseDTO;
import com.example.notificacionservice.service.NotificacionService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController // 1. Indica que esta clase manejará endpoints REST y convertirá las respuestas automáticamente a JSON
@RequestMapping("/api/v1/notificaciones") // 2. Define el prefijo de la ruta URL para acceder a este servicio
@Tag(name = "Notificaciones", description = "Registro y envío de notificaciones al cliente.")
public class NotificacionController {

    private final NotificacionService notificacionService; // 3. Conexión con la capa de negocio

    // 4. Constructor para la inyección del servicio
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping // 5. Intercepta las solicitudes HTTP POST dirigidas a /api/v1/notificaciones
    @Operation(summary = "Enviar notificación", description = "Registra una notificación enviada a un cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notificación registrada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacionResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"clienteId\":1,\"medio\":\"EMAIL\",\"mensaje\":\"Su vehículo está listo\",\"fechaEnvio\":\"2026-06-19T10:30:00\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la notificación a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacionRequestDTO.class), examples = @ExampleObject(value = "{\"clienteId\":1,\"medio\":\"EMAIL\",\"mensaje\":\"Su vehículo está listo\"}")))
    public ResponseEntity<NotificacionResponseDTO> enviarNotificacion(@Valid @RequestBody NotificacionRequestDTO request) {
        // @Valid: Analiza los atributos del DTO. Si 'mensaje' viene vacío, frena el flujo lanzando un 400 Bad Request
        // @RequestBody: Indica a Spring que tome el JSON que viene en el cuerpo del HTTP y lo transforme en el objeto Java 'request'
        
        NotificacionResponseDTO respuesta = notificacionService.registrarNotificacion(request);
        
        // 6. Retorna la respuesta envuelta en un ResponseEntity con el código HTTP 201 CREATED (Alineado a la pauta)
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}