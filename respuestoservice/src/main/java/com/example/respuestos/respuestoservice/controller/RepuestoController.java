package com.example.respuestos.respuestoservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.respuestos.respuestoservice.dto.request.RepuestoRequestDTO;
import com.example.respuestos.respuestoservice.dto.response.RepuestoResponseDTO;
import com.example.respuestos.respuestoservice.service.RepuestoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/repuestos")
@Validated
public class RepuestoController {

    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @PostMapping
    public ResponseEntity<RepuestoResponseDTO> crearRepuesto(@Valid @RequestBody RepuestoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repuestoService.guardar(request));
    }

    @GetMapping
    public ResponseEntity<List<RepuestoResponseDTO>> listarRepuestos() {
        return ResponseEntity.ok(repuestoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepuestoResponseDTO> obtenerRepuestoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repuestoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepuestoResponseDTO> actualizarRepuesto(
            @PathVariable Long id,
            @Valid @RequestBody RepuestoRequestDTO request) {
        return ResponseEntity.ok(repuestoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRepuesto(@PathVariable Long id) {
        boolean eliminado = repuestoService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Repuesto eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repuesto no encontrado");
    }
}
