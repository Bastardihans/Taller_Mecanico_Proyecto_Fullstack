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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehículos", description = "Administración de vehículos asociados a clientes y su estado de reparación.")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping
    @Operation(summary = "Registrar vehículo", description = "Crea un vehículo asociado a un cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"clienteId\":1,\"patente\":\"ABCD12\",\"marca\":\"Toyota\",\"modelo\":\"Yaris\",\"anio\":2020,\"enReparacion\":false}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "La patente ya está registrada"),
            @ApiResponse(responseCode = "502", description = "No fue posible validar el cliente externo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del vehículo a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoRequestDTO.class), examples = @ExampleObject(value = "{\"clienteId\":1,\"patente\":\"ABCD12\",\"marca\":\"Toyota\",\"modelo\":\"Yaris\",\"anio\":2020,\"enReparacion\":false}")))
    public ResponseEntity<VehiculoResponseDTO> registrarVehiculo(@Valid @RequestBody VehiculoRequestDTO request) {
        VehiculoResponseDTO nuevo = vehiculoService.registrarVehiculo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    @Operation(summary = "Listar vehículos", description = "Devuelve todos los vehículos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de vehículos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehiculoResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<VehiculoResponseDTO>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vehículo por ID", description = "Busca un vehículo usando su identificador interno.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno del vehículo", example = "1", required = true)
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    @GetMapping("/patente/{patente}")
    @Operation(summary = "Buscar vehículo por patente", description = "Busca un vehículo utilizando su patente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "patente", description = "Patente del vehículo", example = "ABCD12", required = true)
    public ResponseEntity<VehiculoResponseDTO> obtenerPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.buscarPorPatente(patente));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar vehículos por cliente", description = "Devuelve los vehículos asociados a un cliente determinado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de vehículos del cliente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehiculoResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "clienteId", description = "ID del cliente", example = "1", required = true)
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.buscarPorCliente(clienteId));
    }

    @PutMapping("/{id}/reparacion")
    @Operation(summary = "Cambiar estado de reparación", description = "Actualiza si un vehículo está en reparación o no.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno del vehículo", example = "1", required = true)
    @Parameter(name = "enReparacion", description = "Indica si queda en reparación", example = "true", required = true)
    public ResponseEntity<VehiculoResponseDTO> cambiarEstadoReparacion(@PathVariable Long id, @RequestParam boolean enReparacion) {
        return ResponseEntity.ok(vehiculoService.actualizarEstadoReparacion(id, enReparacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo existente del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno del vehículo", example = "1", required = true)
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}