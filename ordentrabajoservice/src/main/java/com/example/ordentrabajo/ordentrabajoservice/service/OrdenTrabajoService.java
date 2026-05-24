
package com.example.ordentrabajo.ordentrabajoservice.service;

import com.example.ordentrabajo.ordentrabajoservice.client.MecanicoClienteFeign;
import com.example.ordentrabajo.ordentrabajoservice.client.VehiculoClienteFeign;
import com.example.ordentrabajo.ordentrabajoservice.dto.request.OrdenTrabajoRequestDTO;
import com.example.ordentrabajo.ordentrabajoservice.dto.response.OrdenTrabajoResponseDTO;
import com.example.ordentrabajo.ordentrabajoservice.exceptions.OrdenTrabajoNotFoundException;
import com.example.ordentrabajo.ordentrabajoservice.model.OrdenTrabajoModel;
import com.example.ordentrabajo.ordentrabajoservice.repository.OrdenTrabajoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenTrabajoService {

    private final OrdenTrabajoRepository ordenTrabajoRepository;

    private final VehiculoClienteFeign vehiculoClient;

    private final MecanicoClienteFeign mecanicoClient;

    // LISTAR TODAS
    public List<OrdenTrabajoModel> obtenerTodas() {

        return ordenTrabajoRepository.findAll();
    }

    // OBTENER POR ID
    public OrdenTrabajoModel obtenerPorId(Long id) {

        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() ->
                        new OrdenTrabajoNotFoundException(
                                "Orden de trabajo no encontrada"
                        ));
    }

    // CREAR ORDEN
    public OrdenTrabajoResponseDTO guardar(
            OrdenTrabajoRequestDTO request
    ) {

        // VALIDAR VEHÍCULO EXISTENTE
        vehiculoClient.obtenerVehiculoPorId(
                request.getVehiculoId()
        );

        // VALIDAR MECÁNICO EXISTENTE
        mecanicoClient.obtenerMecanicoPorId(
                request.getMecanicoId()
        );

        OrdenTrabajoModel orden = OrdenTrabajoModel.builder()
                .vehiculoId(request.getVehiculoId())
                .mecanicoId(request.getMecanicoId())
                .servicioId(request.getServicioId())
                .costoRepuestos(request.getCostoRepuestos())
                .fechaIngreso(LocalDateTime.now())
                .descripcionFalla(request.getDescripcionFalla())
                .estado("PENDIENTE")
                .build();

        OrdenTrabajoModel guardada =
                ordenTrabajoRepository.save(orden);

        return mapToResponse(guardada);
    }

    // ACTUALIZAR
    public OrdenTrabajoResponseDTO actualizar(
            Long id,
            OrdenTrabajoRequestDTO request
    ) {

        OrdenTrabajoModel existente = obtenerPorId(id);

        // VALIDAR VEHÍCULO EXISTENTE
        vehiculoClient.obtenerVehiculoPorId(
                request.getVehiculoId()
        );

        // VALIDAR MECÁNICO EXISTENTE
        mecanicoClient.obtenerMecanicoPorId(
                request.getMecanicoId()
        );

        existente.setVehiculoId(request.getVehiculoId());
        existente.setMecanicoId(request.getMecanicoId());
        existente.setServicioId(request.getServicioId());
        existente.setCostoRepuestos(request.getCostoRepuestos());
        existente.setDescripcionFalla(
                request.getDescripcionFalla()
        );

        OrdenTrabajoModel actualizada =
                ordenTrabajoRepository.save(existente);

        return mapToResponse(actualizada);
    }

    // CAMBIAR ESTADO
    public OrdenTrabajoResponseDTO cambiarEstado(
            Long id,
            String estado
    ) {

        OrdenTrabajoModel orden = obtenerPorId(id);

        orden.setEstado(estado);

        OrdenTrabajoModel actualizada =
                ordenTrabajoRepository.save(orden);

        return mapToResponse(actualizada);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {

        if (!ordenTrabajoRepository.existsById(id)) {
            return false;
        }

        ordenTrabajoRepository.deleteById(id);

        return true;
    }

    // MAP DTO RESPONSE
    private OrdenTrabajoResponseDTO mapToResponse(
            OrdenTrabajoModel orden
    ) {

        return OrdenTrabajoResponseDTO.builder()
                .id(orden.getId())
                .vehiculoId(orden.getVehiculoId())
                .mecanicoId(orden.getMecanicoId())
                .servicioId(orden.getServicioId())
                .costoRepuestos(orden.getCostoRepuestos())
                .fechaIngreso(orden.getFechaIngreso())
                .descripcionFalla(orden.getDescripcionFalla())
                .estado(orden.getEstado())
                .build();
    }


    // BUSCAR POR VEHICULO
    public List<OrdenTrabajoModel> obtenerPorVehiculo(
        Long vehiculoId) {

    return ordenTrabajoRepository
            .findByVehiculoId(vehiculoId);
        }

    // BUSCAR POR MECANICO
    public List<OrdenTrabajoModel> obtenerPorMecanico(
        Long mecanicoId) {

    return ordenTrabajoRepository
            .findByMecanicoId(mecanicoId);}

    // BUSCAR POR ESTADO
    public List<OrdenTrabajoModel> obtenerPorEstado(
        String estado) {

    return ordenTrabajoRepository
            .findByEstado(estado);}

}

