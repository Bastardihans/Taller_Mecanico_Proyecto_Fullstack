package com.example.vehiculo.vehiculoservice.controller;

import com.example.vehiculo.vehiculoservice.dto.request.VehiculoRequestDTO;
import com.example.vehiculo.vehiculoservice.dto.response.VehiculoResponseDTO;
import com.example.vehiculo.vehiculoservice.modelo.Vehiculo;
import com.example.vehiculo.vehiculoservice.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    //  Obtener todos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    //  Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }

    //  Crear vehículo
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> guardar(
            @Valid @RequestBody VehiculoRequestDTO request
    ) {
        VehiculoResponseDTO nuevo = vehiculoService.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Actualizar vehículo
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(
            @PathVariable 
            long id,
            @RequestBody Vehiculo vehiculo
    ) {
        Vehiculo actualizado = vehiculoService.actualizar(id, vehiculo);
        return ResponseEntity.ok(actualizado);
    }

    //  Eliminar vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        boolean eliminado = vehiculoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok("Vehículo eliminado");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Vehículo no encontrado");
    }

    //  Buscar por patente (IMPORTANTE PARA TU JWT + CRUD)
    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.buscarPorPatente(patente));
    }


    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Vehiculo>> obtenerPorClienteId(
        @PathVariable Long clienteId) {

    return ResponseEntity.ok(
            vehiculoService.obtenerPorClienteId(clienteId)
    );
}


}