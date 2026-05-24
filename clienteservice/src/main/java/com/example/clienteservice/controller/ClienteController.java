package com.example.clienteservice.controller;

import com.example.clienteservice.model.ClienteModel;


import com.example.clienteservice.dto.request.ClienteRequestDTO;
import com.example.clienteservice.dto.response.ClienteResponseDTO;
import com.example.clienteservice.service.ClienteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // CREAR CLIENTE
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(
            @Valid @RequestBody ClienteRequestDTO request
    ) {

        ClienteResponseDTO nuevo = clienteService.guardar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

  
  

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
            clienteService.obtenerPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody ClienteRequestDTO request) {

    return ResponseEntity.ok(
            clienteService.actualizar(id, request)
    );
}



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

    boolean eliminado = clienteService.eliminar(id);

    if (eliminado) {
        return ResponseEntity.ok("Cliente eliminado");
    }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Cliente no encontrado");
    }   

    
    @GetMapping
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
}
}


