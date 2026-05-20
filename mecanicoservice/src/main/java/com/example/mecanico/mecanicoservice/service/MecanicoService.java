package com.example.mecanico.mecanicoservice.service;
import com.example.mecanico.mecanicoservice.dto.request.MecanicoRequestDTO;
import com.example.mecanico.mecanicoservice.dto.MecanicoResponseDTO;
import com.example.mecanico.mecanicoservice.model.MecanicoModel;
import com.example.mecanico.mecanicoservice.repository.MecanicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean eliminar(Long id) {

        if (!mecanicoRepository.existsById(id)) {
            return false;
        }

        mecanicoRepository.deleteById(id);

        return true;

}

}