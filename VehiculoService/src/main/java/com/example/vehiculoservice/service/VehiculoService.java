package com.example.vehiculoservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vehiculoservice.client.ClienteClient;
import com.example.vehiculoservice.dto.VehiculoRequestDTO;
import com.example.vehiculoservice.dto.VehiculoResponseDTO;
import com.example.vehiculoservice.exception.VehiculoNotFoundException;
import com.example.vehiculoservice.model.Vehiculo;
import com.example.vehiculoservice.repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteClient clienteClient;

    @Transactional
    public VehiculoResponseDTO registrarVehiculo(VehiculoRequestDTO request) {
        // 1. Validar duplicados de patente
        if (vehiculoRepository.existsByPatente(request.getPatente().toUpperCase())) {
            throw new IllegalArgumentException("La patente '" + request.getPatente() + "' ya está registrada.");
        }

        // 2. Comprobar existencia del cliente usando el cliente Feign de forma segura
        // 2. Comprobar existencia del cliente usando el cliente Feign de forma segura
        try {
            clienteClient.obtenerClientePorId(request.getClienteId());
        } catch (Exception e) {
            // ⬇️ ESTA LÍNEA ES NUEVA: Nos dirá qué rompió a Feign exactamente ⬇️
            e.printStackTrace(); 
            throw new IllegalArgumentException("El cliente con ID " + request.getClienteId() + " no existe en el sistema o el servicio externo está caído.");
        }

        // 3. Mapear DTO a Entidad pura de base de datos
        Vehiculo vehiculo = Vehiculo.builder()
                .clienteId(request.getClienteId())
                .patente(request.getPatente().toUpperCase())
                .marca(request.getMarca())
                .modelo(request.getModelo())
                .anio(request.getAnio())
                .enReparacion(request.getEnReparacion())
                .build();

        // 4. Guardar y retornar respuesta limpia
        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return mapearADTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> listarTodos() {
        return vehiculoRepository.findAll().stream().map(this::mapearADTO).toList();
    }

    @Transactional(readOnly = true)
    public VehiculoResponseDTO buscarPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new VehiculoNotFoundException("Vehículo con ID " + id + " no encontrado."));
        return mapearADTO(vehiculo);
    }

    @Transactional(readOnly = true)
    public VehiculoResponseDTO buscarPorPatente(String patente) {
        Vehiculo vehiculo = vehiculoRepository.findByPatente(patente.toUpperCase())
                .orElseThrow(() -> new VehiculoNotFoundException("Vehículo con patente '" + patente + "' no encontrado."));
        return mapearADTO(vehiculo);
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorCliente(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId).stream().map(this::mapearADTO).toList();
    }

    @Transactional
    public VehiculoResponseDTO actualizarEstadoReparacion(Long id, boolean enReparacion) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new VehiculoNotFoundException("Vehículo con ID " + id + " no encontrado."));
        vehiculo.setEnReparacion(enReparacion);
        return mapearADTO(vehiculoRepository.save(vehiculo));
    }

    @Transactional
    public void eliminarVehiculo(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new VehiculoNotFoundException("Vehículo con ID " + id + " no encontrado.");
        }
        vehiculoRepository.deleteById(id);
    }

    // Convertidor Helper para encapsular datos hacia la capa de presentación
    private VehiculoResponseDTO mapearADTO(Vehiculo vehiculo) {
        return VehiculoResponseDTO.builder()
                .id(vehiculo.getId())
                .clienteId(vehiculo.getClienteId())
                .patente(vehiculo.getPatente())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .anio(vehiculo.getAnio())
                .enReparacion(vehiculo.getEnReparacion())
                .build();
    }
}