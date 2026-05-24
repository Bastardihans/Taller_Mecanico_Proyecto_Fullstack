package com.example.mecanico.mecanicoservice.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mecanico.mecanicoservice.dto.MecanicoResponseDTO;
import com.example.mecanico.mecanicoservice.dto.request.MecanicoRequestDTO;
import com.example.mecanico.mecanicoservice.model.MecanicoModel;
import com.example.mecanico.mecanicoservice.repository.MecanicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MecanicoService {
    private final MecanicoRepository mecanicoRepository;

    public List<MecanicoModel> obtenerTodos() {
        return mecanicoRepository.findAll();
    }

    public MecanicoModel obtenerPorId(Long id) {

        return mecanicoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mecánico no encontrado"));
    }

    public MecanicoResponseDTO guardar(MecanicoRequestDTO request) {

        if (mecanicoRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El rut ya existe");
        }

        MecanicoModel mecanico = MecanicoModel.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .especialidad(request.getEspecialidad())
                .telefono(request.getTelefono())
                .disponible(request.isDisponible())
                .build();

        MecanicoModel guardado = mecanicoRepository.save(mecanico);

        return MecanicoResponseDTO.builder()
                .id(guardado.getId())
                .rut(guardado.getRut())
                .nombre(guardado.getNombre())
                .especialidad(guardado.getEspecialidad())
                .telefono(guardado.getTelefono())
                .disponible(guardado.isDisponible())
                .build();
    }

    public MecanicoResponseDTO actualizar(Long id, MecanicoRequestDTO request) {
        MecanicoModel existente = mecanicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));

        existente.setRut(request.getRut());
        existente.setNombre(request.getNombre());
        existente.setEspecialidad(request.getEspecialidad());
        existente.setTelefono(request.getTelefono());
        existente.setDisponible(request.isDisponible());

        MecanicoModel actualizado = mecanicoRepository.save(existente);

        return MecanicoResponseDTO.builder()
                .id(actualizado.getId())
                .rut(actualizado.getRut())
                .nombre(actualizado.getNombre())
                .especialidad(actualizado.getEspecialidad())
                .telefono(actualizado.getTelefono())
                .disponible(actualizado.isDisponible())
                .build();
    }

    public boolean eliminar(Long id) {

        if (!mecanicoRepository.existsById(id)) {
            return false;
        }

        mecanicoRepository.deleteById(id);

        return true;

}

}