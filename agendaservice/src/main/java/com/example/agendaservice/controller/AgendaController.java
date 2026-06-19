package com.example.agendaservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.agendaservice.dto.AgendaRequestDTO;
import com.example.agendaservice.dto.AgendaResponseDTO;
import com.example.agendaservice.service.AgendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas")
@Tag(name = "Agenda", description = "Gestión de citas y reservas del taller mecánico.")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cita", description = "Registra una nueva cita en la agenda del taller.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"clienteId\":1,\"fechaHora\":\"2026-06-20T10:30:00\",\"motivo\":\"Mantención preventiva del vehículo\",\"estado\":\"PENDIENTE\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "502", description = "No fue posible validar el cliente externo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la cita a crear", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaRequestDTO.class), examples = @ExampleObject(value = "{\"clienteId\":1,\"fechaHora\":\"2026-06-20T10:30:00\",\"motivo\":\"Mantención preventiva del vehículo\"}")))
    public ResponseEntity<AgendaResponseDTO> agendarCita(@Valid @RequestBody AgendaRequestDTO request) {
        AgendaResponseDTO respuesta = agendaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}