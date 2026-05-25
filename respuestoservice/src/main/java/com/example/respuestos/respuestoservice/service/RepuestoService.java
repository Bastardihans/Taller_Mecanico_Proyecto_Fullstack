package com.example.respuestos.respuestoservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.respuestos.respuestoservice.dto.request.RepuestoRequestDTO;
import com.example.respuestos.respuestoservice.dto.response.RepuestoResponseDTO;
import com.example.respuestos.respuestoservice.exceptions.RepuestoNotFoundException;
import com.example.respuestos.respuestoservice.model.RepuestoModel;
import com.example.respuestos.respuestoservice.repository.RepuestoRepository;

@Service
public class RepuestoService {

    private final RepuestoRepository repuestoRepository;

    public RepuestoService(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }

    public RepuestoResponseDTO guardar(RepuestoRequestDTO request) {
        RepuestoModel repuesto = RepuestoModel.builder()
                .nombre(request.getNombre())
                .stock(request.getStock())
                .precioUnitario(request.getPrecioUnitario())
                .build();

        return mapToResponse(repuestoRepository.save(repuesto));
    }

    public List<RepuestoResponseDTO> obtenerTodos() {
        return repuestoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RepuestoResponseDTO obtenerPorId(Long id) {
        return repuestoRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RepuestoNotFoundException("Repuesto no encontrado con id " + id));
    }

    public RepuestoResponseDTO actualizar(Long id, RepuestoRequestDTO request) {
        RepuestoModel existente = repuestoRepository.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException("Repuesto no encontrado con id " + id));

        existente.setNombre(request.getNombre());
        existente.setStock(request.getStock());
        existente.setPrecioUnitario(request.getPrecioUnitario());

        return mapToResponse(repuestoRepository.save(existente));
    }

    public boolean eliminar(Long id) {
        if (!repuestoRepository.existsById(id)) {
            return false;
        }
        repuestoRepository.deleteById(id);
        return true;
    }

    private RepuestoResponseDTO mapToResponse(RepuestoModel model) {
        RepuestoResponseDTO response = new RepuestoResponseDTO();
        response.setId(model.getId());
        response.setNombre(model.getNombre());
        response.setStock(model.getStock());
        response.setPrecioUnitario(model.getPrecioUnitario());
        return response;
    }
}
