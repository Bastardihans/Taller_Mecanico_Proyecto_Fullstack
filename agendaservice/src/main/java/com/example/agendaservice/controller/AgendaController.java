package com.example.agendaservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // <- CLAVE: Activa las validaciones de la PPT
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.agendaservice.dto.AgendaRequestDTO;
import com.example.agendaservice.dto.AgendaResponseDTO;
import com.example.agendaservice.service.AgendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas") // URL base de tu servicio
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping // Cuando manden un HTTP POST a /api/v1/agendas
    public ResponseEntity<AgendaResponseDTO> agendarCita(@Valid @RequestBody AgendaRequestDTO request) {
        // El @Valid le dice a Spring: "Revisa el DTO antes de entrar". Si falla, lanza 400 Bad Request
        // El @RequestBody le dice: "El JSON que viene por internet conviértelo en este objeto Java"
        
        AgendaResponseDTO respuesta = agendaService.crearCita(request);
        
        // Usamos ResponseEntity para responder con un código 201 CREATED (Ideal para inserciones)
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}