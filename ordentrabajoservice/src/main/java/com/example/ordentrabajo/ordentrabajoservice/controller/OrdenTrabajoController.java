package com.example.ordentrabajo.ordentrabajoservice.controller;

import com.example.ordentrabajo.ordentrabajoservice.dto.request.OrdenTrabajoRequestDTO;
import com.example.ordentrabajo.ordentrabajoservice.dto.response.OrdenTrabajoResponseDTO;
import com.example.ordentrabajo.ordentrabajoservice.model.OrdenTrabajoModel;
import com.example.ordentrabajo.ordentrabajoservice.service.OrdenTrabajoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
@Tag(name = "Órdenes de trabajo", description = "Gestión de órdenes de trabajo, estados y asignaciones.")
public class OrdenTrabajoController {

    private final OrdenTrabajoService ordenTrabajoService;

    @GetMapping
    @Operation(summary = "Listar órdenes de trabajo", description = "Devuelve todas las órdenes registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de órdenes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrdenTrabajoModel.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<OrdenTrabajoModel>> obtenerTodas() {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerTodas()
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar orden de trabajo", description = "Actualiza la información de una orden existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno de la orden", example = "1", required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos nuevos de la orden", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoRequestDTO.class), examples = @ExampleObject(value = "{\"vehiculoId\":1,\"mecanicoId\":2,\"servicioId\":3,\"costoRepuestos\":25000.0,\"descripcionFalla\":\"Fuga de aceite\",\"estado\":\"EN_PROCESO\"}")))
    public ResponseEntity<OrdenTrabajoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrdenTrabajoRequestDTO request
    ) {
        return ResponseEntity.ok(
                ordenTrabajoService.actualizar(id, request)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Busca una orden de trabajo por su identificador interno.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoModel.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno de la orden", example = "1", required = true)
    public ResponseEntity<OrdenTrabajoModel> obtenerPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(summary = "Registrar orden de trabajo", description = "Crea una orden nueva a partir de un vehículo, mecánico y servicio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Orden creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"vehiculoId\":1,\"mecanicoId\":2,\"servicioId\":3,\"costoRepuestos\":25000.0,\"fechaIngreso\":\"2026-06-19T10:30:00\",\"descripcionFalla\":\"Fuga de aceite\",\"estado\":\"PENDIENTE\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Entidad relacionada no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la orden a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenTrabajoRequestDTO.class), examples = @ExampleObject(value = "{\"vehiculoId\":1,\"mecanicoId\":2,\"servicioId\":3,\"costoRepuestos\":25000.0,\"descripcionFalla\":\"Fuga de aceite\",\"estado\":\"PENDIENTE\"}")))
    public ResponseEntity<OrdenTrabajoResponseDTO> guardar(
            @Valid @RequestBody OrdenTrabajoRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenTrabajoService.guardar(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar orden de trabajo", description = "Elimina una orden de trabajo existente del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "id", description = "ID interno de la orden", example = "1", required = true)
    public ResponseEntity<String> eliminar(
            @PathVariable Long id
    ) {

        boolean eliminado =
                ordenTrabajoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok(
                    "Orden de trabajo eliminada"
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Orden de trabajo no encontrada");
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    @Operation(summary = "Listar órdenes por vehículo", description = "Devuelve las órdenes asociadas a un vehículo específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de órdenes por vehículo", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrdenTrabajoModel.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "vehiculoId", description = "ID del vehículo", example = "1", required = true)
    public ResponseEntity<List<OrdenTrabajoModel>>
    obtenerPorVehiculo(
            @PathVariable Long vehiculoId
    ) {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerPorVehiculo(
                        vehiculoId
                )
        );
    }

    @GetMapping("/mecanico/{mecanicoId}")
    @Operation(summary = "Listar órdenes por mecánico", description = "Devuelve las órdenes asignadas a un mecánico específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de órdenes por mecánico", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrdenTrabajoModel.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "mecanicoId", description = "ID del mecánico", example = "2", required = true)
    public ResponseEntity<List<OrdenTrabajoModel>>
    obtenerPorMecanico(
            @PathVariable Long mecanicoId
    ) {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerPorMecanico(
                        mecanicoId
                )
        );
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar órdenes por estado", description = "Devuelve las órdenes filtradas por su estado actual.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de órdenes por estado", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrdenTrabajoModel.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Parameter(name = "estado", description = "Estado de la orden", example = "PENDIENTE", required = true)
    public ResponseEntity<List<OrdenTrabajoModel>>
    obtenerPorEstado(
            @PathVariable String estado
    ) {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerPorEstado(
                        estado
                )
        );
    }
}
