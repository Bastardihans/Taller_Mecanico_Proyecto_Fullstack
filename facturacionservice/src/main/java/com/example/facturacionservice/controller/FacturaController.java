package com.example.facturacionservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; // Activa Bean Validation
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // Control total HTTP de la PPT

import com.example.facturacionservice.dto.FacturaRequestDTO;
import com.example.facturacionservice.dto.FacturaResponseDTO;
import com.example.facturacionservice.dto.FacturaUpdateDTO;
import com.example.facturacionservice.service.FacturaService;

import jakarta.validation.Valid;

@RestController // 1. Expone respuestas automáticas estructuradas en JSON
@RequestMapping("/api/v1/facturas") // 2. URL raíz para el módulo de facturación
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping // 3. Atrapa peticiones HTTP POST dirigidas a /api/v1/facturas (Generar boleta)
    public ResponseEntity<FacturaResponseDTO> crearFactura(@Valid @RequestBody FacturaRequestDTO request) {
        // @Valid: Detiene el flujo con 400 Bad Request si el DTO no cumple las reglas
        
        FacturaResponseDTO respuesta = facturaService.emitirFactura(request);
        
        // 4. Responde con un código semántico 201 CREATED al cliente
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<?> listarFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> actualizarFactura(
            @PathVariable Long id,
            @Valid @RequestBody FacturaUpdateDTO request
    ) {
        return ResponseEntity.ok(facturaService.actualizarEstadoPago(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFactura(@PathVariable Long id) {
        boolean eliminado = facturaService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Factura eliminada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Factura no encontrada");
    }
}