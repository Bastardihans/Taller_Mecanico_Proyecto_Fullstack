package com.example.clienteservice.controller;

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

import com.example.clienteservice.dto.request.ClienteRequestDTO;
import com.example.clienteservice.dto.response.ClienteResponseDTO;
import com.example.clienteservice.model.ClienteModel;
import com.example.clienteservice.service.ClienteService;

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

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes del taller mecánico.")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(tags = {"Clientes"}, summary = "Registrar cliente", description = "Crea un cliente nuevo en el sistema del taller.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class), examples = @ExampleObject(value = "{\"id\":1,\"rut\":\"12.345.678-9\",\"nombre\":\"Juan\",\"apellido\":\"Pérez\",\"telefono\":\"912345678\",\"correo\":\"juan@mail.com\",\"direccion\":\"Av. Central 123\"}"))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "El RUT ya existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del cliente a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteRequestDTO.class), examples = @ExampleObject(value = "{\"rut\":\"12.345.678-9\",\"nombre\":\"Juan\",\"apellido\":\"Pérez\",\"telefono\":\"912345678\",\"correo\":\"juan@mail.com\",\"direccion\":\"Av. Central 123\"}")))
    public ResponseEntity<ClienteResponseDTO> guardar(
            @Valid @RequestBody ClienteRequestDTO request
    ) {

        ClienteResponseDTO nuevo = clienteService.guardar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

  
  

    @GetMapping("/{id}")
    @Operation(tags = {"Clientes"}, summary = "Obtener cliente por ID", description = "Devuelve el detalle de un cliente usando su identificador interno.", parameters = {
        @Parameter(name = "id", description = "ID interno del cliente", example = "1", required = true)
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteModel.class))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
            clienteService.obtenerPorId(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Clientes"}, summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente.", parameters = {
        @Parameter(name = "id", description = "ID interno del cliente", example = "1", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteModel.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "El RUT ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos nuevos del cliente", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteRequestDTO.class), examples = @ExampleObject(value = "{\"rut\":\"12.345.678-9\",\"nombre\":\"Juan\",\"apellido\":\"Pérez\",\"telefono\":\"912345678\",\"correo\":\"juan@mail.com\",\"direccion\":\"Av. Central 123\"}")))
    public ResponseEntity<ClienteModel> actualizar(
        @PathVariable("id") Long id,
        @Valid @RequestBody ClienteRequestDTO request) {

    return ResponseEntity.ok(
            clienteService.actualizar(id, request)
    );
}



    @DeleteMapping("/{id}")
    @Operation(tags = {"Clientes"}, summary = "Eliminar cliente", description = "Elimina un cliente del sistema si existe.", parameters = {
        @Parameter(name = "id", description = "ID interno del cliente", example = "1", required = true)
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {

    boolean eliminado = clienteService.eliminar(id);

    if (eliminado) {
        return ResponseEntity.ok("Cliente eliminado");
    }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Cliente no encontrado");
    }   

    
    @GetMapping
    @Operation(tags = {"Clientes"}, summary = "Listar clientes", description = "Devuelve el listado completo de clientes del taller.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de clientes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClienteModel.class)))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
}
}


