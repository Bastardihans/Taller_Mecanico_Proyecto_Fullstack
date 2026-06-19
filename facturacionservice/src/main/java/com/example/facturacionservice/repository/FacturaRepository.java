package com.example.facturacionservice.repository;

import com.example.facturacionservice.model.FacturaModel; // Importa el modelo de la factura
import org.springframework.data.jpa.repository.JpaRepository; // Carga la interfaz genérica de Spring Data
import org.springframework.stereotype.Repository; // Marca la clase como componente de persistencia

@Repository // 1. Registra esta interfaz en el contenedor de inversión de control de Spring
public interface FacturaRepository extends JpaRepository<FacturaModel, Long> {
    // 2. Hereda automáticamente todas las funciones de escritura y lectura en la base de datos MySQL
}