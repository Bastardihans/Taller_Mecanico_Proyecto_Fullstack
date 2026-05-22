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

@RestController
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
public class OrdenTrabajoController {

    private final OrdenTrabajoService ordenTrabajoService;

    @GetMapping
    public ResponseEntity<List<OrdenTrabajoModel>> obtenerTodas() {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajoModel> obtenerPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ordenTrabajoService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<OrdenTrabajoResponseDTO> guardar(
            @Valid @RequestBody OrdenTrabajoRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenTrabajoService.guardar(request));
    }

    @DeleteMapping("/{id}")
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
