package com.example.servicioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Importamos el modelo
import org.springframework.stereotype.Repository; // Importamos la superclase de Spring Data

import com.example.servicioservice.model.ServicioModel; // Indica que es un componente de persistencia

@Repository // 1. Registra esta interfaz en el contenedor de Spring para poder inyectarla luego
public interface ServicioRepository extends JpaRepository<ServicioModel, Long> {
    // 2. Al extender de JpaRepository<Modelo, Tipo_ID>, Spring implementa automáticamente por debajo:
    // .save() para guardar, .findAll() para listar, .findById() para buscar, .deleteById() para borrar.
}