package com.example.vehiculoservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vehiculoservice.dto.VehiculoRequestDTO;
import com.example.vehiculoservice.dto.VehiculoResponseDTO;
import com.example.vehiculoservice.service.VehiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> registrarVehiculo(@Valid @RequestBody VehiculoRequestDTO request) {
        VehiculoResponseDTO nuevo = vehiculoService.registrarVehiculo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.buscarPorPatente(patente));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.buscarPorCliente(clienteId));
    }

    @PutMapping("/{id}/reparacion")
    public ResponseEntity<VehiculoResponseDTO> cambiarEstadoReparacion(@PathVariable Long id, @RequestParam boolean enReparacion) {
        return ResponseEntity.ok(vehiculoService.actualizarEstadoReparacion(id, enReparacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}