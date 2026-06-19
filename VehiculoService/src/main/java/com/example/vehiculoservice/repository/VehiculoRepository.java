package com.example.vehiculoservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vehiculoservice.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    // Método para comprobar si una patente ya existe antes de registrar un vehículo
    boolean existsByPatente(String patente);
    
    // Método para buscar un vehículo específico por su patente
    Optional<Vehiculo> findByPatente(String patente);
    
    // Método para obtener todos los vehículos asignados a un cliente específico
    List<Vehiculo> findByClienteId(Long clienteId);
}