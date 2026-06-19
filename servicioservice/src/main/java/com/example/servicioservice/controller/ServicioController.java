package com.example.servicioservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; // Habilita la revisión de las validaciones del DTO (@NotBlank, @Positive)
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importa los códigos de estado HTTP
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; // Importa la clase de control total HTTP de la PPT
import org.springframework.web.bind.annotation.RequestMapping; // Importa las anotaciones de las rutas REST
import org.springframework.web.bind.annotation.RestController;

import com.example.servicioservice.dto.ServicioRequestDTO;
import com.example.servicioservice.dto.ServicioResponseDTO;
import com.example.servicioservice.service.ServicioService;

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

@RestController // 1. Le dice a Spring que esta clase es una API REST que devuelve respuestas en formato JSON
@RequestMapping("/api/v1/servicios") // 2. URL base de este microservicio
@Tag(name = "Servicios", description = "Catálogo de servicios técnicos y tarifas estandarizadas.")
public class ServicioController {

    private final ServicioService servicioService; // 3. Conectamos con el cerebro del servicio

    // 4. Inyección del servicio por constructor
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping // 5. Atrapa las peticiones HTTP POST dirigidas a /api/v1/servicios (Crear)
        @Operation(summary = "Registrar servicio", description = "Crea un nuevo servicio técnico en el catálogo.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Servicio creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServicioResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"nombre\":\"Cambio de aceite\",\"descripcion\":\"Cambio de aceite y filtro\",\"precioBase\":25000.0}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del servicio a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServicioRequestDTO.class), examples = @ExampleObject(value = "{\"nombre\":\"Cambio de aceite\",\"descripcion\":\"Cambio de aceite y filtro\",\"precioBase\":25000.0}")))
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioRequestDTO request) {
        // @Valid: Valida el formulario antes de procesarlo. Si falla, corta el flujo y lanza un 400 Bad Request
        // @RequestBody: Captura el JSON enviado por internet y lo convierte en el objeto 'request'
        
        ServicioResponseDTO respuesta = servicioService.guardarServicio(request);
        
        // 6. Respondemos usando ResponseEntity con el código semántico 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping // 7. Atrapa las peticiones HTTP GET dirigidas a /api/v1/servicios (Listar todo)
        @Operation(summary = "Listar servicios", description = "Devuelve todos los servicios técnicos disponibles.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de servicios", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ServicioResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    public ResponseEntity<List<ServicioResponseDTO>> listarServicios() {
        List<ServicioResponseDTO> lista = servicioService.obtenerTodosLosServicios();
        
        // 8. Respondemos con un código 200 OK porque es una consulta de lectura exitosa
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
        @Operation(summary = "Obtener servicio por ID", description = "Busca un servicio técnico usando su identificador interno.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServicioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del servicio", example = "1", required = true)
    public ResponseEntity<ServicioResponseDTO> obtenerServicioPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                servicioService.obtenerServicioPorId(id)
        );
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar servicio", description = "Actualiza un servicio técnico existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServicioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del servicio", example = "1", required = true)
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos nuevos del servicio", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServicioRequestDTO.class), examples = @ExampleObject(value = "{\"nombre\":\"Cambio de aceite\",\"descripcion\":\"Incluye filtro de aceite\",\"precioBase\":30000.0}")))
    public ResponseEntity<ServicioResponseDTO> actualizarServicio(
            @PathVariable Long id,
            @Valid @RequestBody ServicioRequestDTO request
    ) {
        return ResponseEntity.ok(
                servicioService.actualizarServicio(id, request)
        );
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar servicio", description = "Elimina un servicio técnico existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @Parameter(name = "id", description = "ID interno del servicio", example = "1", required = true)
    public ResponseEntity<String> eliminarServicio(@PathVariable Long id) {
        boolean eliminado = servicioService.eliminarServicio(id);
        if (eliminado) {
            return ResponseEntity.ok("Servicio eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado");
    }
}