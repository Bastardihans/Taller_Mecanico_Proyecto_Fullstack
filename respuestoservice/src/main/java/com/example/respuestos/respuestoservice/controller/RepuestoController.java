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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/repuestos")
@Validated
@Tag(name = "Repuestos", description = "Gestión de inventario, stock y precios de repuestos.")
public class RepuestoController {

    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @PostMapping
    @Operation(summary = "Registrar repuesto", description = "Crea un repuesto nuevo en el inventario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Repuesto creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepuestoResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"nombre\":\"Filtro de aceite\",\"stock\":20,\"precioUnitario\":8500.0}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del repuesto a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepuestoRequestDTO.class), examples = @ExampleObject(value = "{\"nombre\":\"Filtro de aceite\",\"stock\":20,\"precioUnitario\":8500.0}")))
    public ResponseEntity<RepuestoResponseDTO> crearRepuesto(@Valid @RequestBody RepuestoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repuestoService.guardar(request));
    }

    @GetMapping
        @Operation(summary = "Listar repuestos", description = "Devuelve todo el inventario de repuestos.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de repuestos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RepuestoResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<List<RepuestoResponseDTO>> listarRepuestos() {
        return ResponseEntity.ok(repuestoService.obtenerTodos());
    }

    @GetMapping("/{id}")
        @Operation(summary = "Obtener repuesto por ID", description = "Busca un repuesto por su identificador interno.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Repuesto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepuestoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del repuesto", example = "1", required = true)
    public ResponseEntity<RepuestoResponseDTO> obtenerRepuestoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repuestoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar repuesto", description = "Actualiza los datos de un repuesto existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Repuesto actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepuestoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del repuesto", example = "1", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos nuevos del repuesto", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepuestoRequestDTO.class), examples = @ExampleObject(value = "{\"nombre\":\"Filtro de aceite premium\",\"stock\":15,\"precioUnitario\":9900.0}")))
    public ResponseEntity<RepuestoResponseDTO> actualizarRepuesto(
            @PathVariable Long id,
            @Valid @RequestBody RepuestoRequestDTO request) {
        return ResponseEntity.ok(repuestoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar repuesto", description = "Elimina un repuesto existente del inventario.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Repuesto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del repuesto", example = "1", required = true)
    public ResponseEntity<String> eliminarRepuesto(@PathVariable Long id) {
        boolean eliminado = repuestoService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Repuesto eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repuesto no encontrado");
    }
}
