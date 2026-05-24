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
import lombok.RequiredArgsConstructor;


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

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MecanicoRequestDTO request
    ) {
        return ResponseEntity.ok(
                mecanicoService.actualizar(id, request)
        );
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
