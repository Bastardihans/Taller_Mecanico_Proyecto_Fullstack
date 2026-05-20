package com.example.vehiculo.vehiculoservice.repositorio;

import com.example.vehiculo.vehiculoservice.modelo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByPatente(String patente);


    List<Vehiculo> findByMarca(String marca);

    List<Vehiculo> findByEnReparacion(boolean enReparacion);

    boolean existsByPatente(String patente);
    
     List<Vehiculo> findByClienteId(Long ClienteId);
}