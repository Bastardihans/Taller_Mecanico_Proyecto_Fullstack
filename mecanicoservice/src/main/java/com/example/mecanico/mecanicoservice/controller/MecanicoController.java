package com.example.mecanico.mecanicoservice.controller;

import com.example.mecanico.mecanicoservice.dto.request.MecanicoRequestDTO;
import com.example.mecanico.mecanicoservice.dto.MecanicoResponseDTO;
import com.example.mecanico.mecanicoservice.service.MecanicoService;
import com.example.mecanico.mecanicoservice.model.MecanicoModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/mecanicos")
@RequiredArgsConstructor

public class MecanicoController {

    private final MecanicoService mecanicoService;

     @GetMapping
    public ResponseEntity<List<MecanicoModel>> listar() {

        return ResponseEntity.ok(
                mecanicoService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoModel> obtenerPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                mecanicoService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<MecanicoResponseDTO> guardar(
            @Valid @RequestBody MecanicoRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mecanicoService.guardar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        boolean eliminado = mecanicoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok("Mecánico eliminado");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Mecánico no encontrado");
    }
}
