package com.example.mecanico.mecanicoservice.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.mecanico.mecanicoservice.dto.MecanicoResponseDTO;
import com.example.mecanico.mecanicoservice.dto.request.MecanicoRequestDTO;
import com.example.mecanico.mecanicoservice.model.MecanicoModel;
import com.example.mecanico.mecanicoservice.service.MecanicoService;

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
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/mecanicos")
@RequiredArgsConstructor
@Tag(name = "Mecánicos", description = "Administración de mecánicos, especialidades y disponibilidad.")

public class MecanicoController {

    private final MecanicoService mecanicoService;

        @GetMapping
        @Operation(tags = {"Mecánicos"}, summary = "Listar mecánicos", description = "Devuelve todos los mecánicos registrados.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de mecánicos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MecanicoModel.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<List<MecanicoModel>> listar() {

        return ResponseEntity.ok(
                mecanicoService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Mecánicos"}, summary = "Obtener mecánico por ID", description = "Busca un mecánico por su identificador interno.", parameters = {
        @Parameter(name = "id", description = "ID interno del mecánico", example = "1", required = true)
    })
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mecánico encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MecanicoModel.class))),
            @ApiResponse(responseCode = "404", description = "Mecánico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<MecanicoModel> obtenerPorId(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                mecanicoService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(tags = {"Mecánicos"}, summary = "Registrar mecánico", description = "Crea un mecánico nuevo en el sistema.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mecánico creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MecanicoResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"rut\":\"12.345.678-9\",\"nombre\":\"Pedro\",\"especialidad\":\"Motor\",\"telefono\":\"912345678\",\"disponible\":true}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del mecánico a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = MecanicoRequestDTO.class), examples = @ExampleObject(value = "{\"rut\":\"12.345.678-9\",\"nombre\":\"Pedro\",\"especialidad\":\"Motor\",\"telefono\":\"912345678\",\"disponible\":true}")))
    public ResponseEntity<MecanicoResponseDTO> guardar(
            @Valid @RequestBody MecanicoRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mecanicoService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Mecánicos"}, summary = "Actualizar mecánico", description = "Actualiza los datos de un mecánico existente.", parameters = {
        @Parameter(name = "id", description = "ID interno del mecánico", example = "1", required = true)
    })
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mecánico actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MecanicoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Mecánico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos nuevos del mecánico", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = MecanicoRequestDTO.class), examples = @ExampleObject(value = "{\"rut\":\"12.345.678-9\",\"nombre\":\"Pedro\",\"especialidad\":\"Frenos\",\"telefono\":\"912345678\",\"disponible\":false}")))
    public ResponseEntity<MecanicoResponseDTO> actualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody MecanicoRequestDTO request
    ) {
        return ResponseEntity.ok(
                mecanicoService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Mecánicos"}, summary = "Eliminar mecánico", description = "Elimina un mecánico del sistema si existe.", parameters = {
        @Parameter(name = "id", description = "ID interno del mecánico", example = "1", required = true)
    })
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mecánico eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Mecánico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {

        boolean eliminado = mecanicoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok("Mecánico eliminado");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Mecánico no encontrado");
    }
}
