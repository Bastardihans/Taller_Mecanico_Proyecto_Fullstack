package com.example.vehiculo.vehiculoservice.service;

import com.example.vehiculo.vehiculoservice.client.ClienteClient;
import com.example.vehiculo.vehiculoservice.dto.request.VehiculoRequestDTO;
import com.example.vehiculo.vehiculoservice.dto.response.VehiculoResponseDTO;
import com.example.vehiculo.vehiculoservice.exceptions.PatenteDuplicadaException;
import com.example.vehiculo.vehiculoservice.modelo.Vehiculo;
import com.example.vehiculo.vehiculoservice.repositorio.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteClient clienteClient;

    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

  

    public Vehiculo obtenerPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehículo no encontrado"));
    }

    public VehiculoResponseDTO guardar(VehiculoRequestDTO request) {

        // Validar patente única
        if (vehiculoRepository.existsByPatente(request.getPatente())) {
            throw new PatenteDuplicadaException("La patente ya existe");
        }

        // VALIDAR QUE EL CLIENTE EXISTA
        clienteClient.obtenerClientePorId(request.getClienteId());

        Vehiculo vehiculo = Vehiculo.builder()
                .clienteId(request.getClienteId())
                .patente(request.getPatente())
                .marca(request.getMarca())
                .modelo(request.getModelo())
                .anio(request.getAnio())
                .enReparacion(request.isEnReparacion())
                .build();

        Vehiculo guardado = vehiculoRepository.save(vehiculo);

        return VehiculoResponseDTO.builder()
                .id(guardado.getId())
                .clienteId(guardado.getClienteId())
                .patente(guardado.getPatente())
                .marca(guardado.getMarca())
                .modelo(guardado.getModelo())
                .anio(guardado.getAnio())
                .enReparacion(guardado.isEnReparacion())
                .build();
    }

    public Vehiculo actualizar(Long id, Vehiculo vehiculo) {

        Vehiculo existente = vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehículo no encontrado"));

        existente.setClienteId(vehiculo.getClienteId());
        existente.setPatente(vehiculo.getPatente());
        existente.setMarca(vehiculo.getMarca());
        existente.setModelo(vehiculo.getModelo());
        existente.setAnio(vehiculo.getAnio());
        existente.setEnReparacion(vehiculo.isEnReparacion());

        return vehiculoRepository.save(existente);
    }

    public boolean eliminar(Long id) {

        if (!vehiculoRepository.existsById(id)) {
            return false;
        }

        vehiculoRepository.deleteById(id);
        return true;
    }

    public Vehiculo buscarPorPatente(String patente) {

        return vehiculoRepository.findByPatente(patente)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Vehículo no encontrado"));
    }

    public List<Vehiculo> obtenerPorClienteId(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }
}