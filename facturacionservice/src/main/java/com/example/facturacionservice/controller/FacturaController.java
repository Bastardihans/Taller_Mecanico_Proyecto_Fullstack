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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController // 1. Expone respuestas automáticas estructuradas en JSON
@RequestMapping("/api/v1/facturas") // 2. URL raíz para el módulo de facturación
@Tag(name = "Facturación", description = "Emisión de facturas, consulta y actualización del estado de pago.")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping // 3. Atrapa peticiones HTTP POST dirigidas a /api/v1/facturas (Generar boleta)
    @Operation(summary = "Crear factura", description = "Emite una factura a partir de una orden de trabajo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Factura creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FacturaResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"ordenId\":10,\"montoTotal\":125000.0,\"estadoPago\":\"PENDIENTE\",\"fechaEmision\":\"2026-06-19T10:30:00\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para emitir la factura", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = FacturaRequestDTO.class), examples = @ExampleObject(value = "{\"ordenId\":10}")))
    public ResponseEntity<FacturaResponseDTO> crearFactura(@Valid @RequestBody FacturaRequestDTO request) {
        // @Valid: Detiene el flujo con 400 Bad Request si el DTO no cumple las reglas
        
        FacturaResponseDTO respuesta = facturaService.emitirFactura(request);
        
        // 4. Responde con un código semántico 201 CREATED al cliente
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
        @Operation(summary = "Listar facturas", description = "Devuelve todas las facturas emitidas.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de facturas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FacturaResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<?> listarFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodas());
    }

    @GetMapping("/{id}")
        @Operation(summary = "Obtener factura por ID", description = "Busca una factura usando su identificador interno.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FacturaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno de la factura", example = "1", required = true)
    public ResponseEntity<FacturaResponseDTO> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar factura", description = "Actualiza el estado de pago de una factura.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FacturaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno de la factura", example = "1", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo estado de pago", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = FacturaUpdateDTO.class), examples = @ExampleObject(value = "{\"estadoPago\":\"PAGADA\"}")))
    public ResponseEntity<FacturaResponseDTO> actualizarFactura(
            @PathVariable Long id,
            @Valid @RequestBody FacturaUpdateDTO request
    ) {
        return ResponseEntity.ok(facturaService.actualizarEstadoPago(id, request));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar factura", description = "Elimina una factura existente del sistema.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno de la factura", example = "1", required = true)
    public ResponseEntity<String> eliminarFactura(@PathVariable Long id) {
        boolean eliminado = facturaService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Factura eliminada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Factura no encontrada");
    }
}