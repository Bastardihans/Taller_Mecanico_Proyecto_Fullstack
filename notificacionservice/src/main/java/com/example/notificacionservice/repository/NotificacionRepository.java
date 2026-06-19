package com.example.notificacionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Importa nuestro modelo de datos
import org.springframework.stereotype.Repository; // Importa la interfaz que provee los métodos CRUD

import com.example.notificacionservice.model.NotificacionModel; // Anotación de Spring que marca la clase como componente de datos

@Repository // 1. Le avisa al contenedor de inversión de control de Spring que este componente gestiona la persistencia
public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {
    // 2. Al heredar de JpaRepository<Entidad, Tipo_ID>, Spring implementa automáticamente en tiempo de ejecución
    // operaciones como: .save(), .findById(), .findAll(), sin necesidad de escribir sentencias SQL manuales.
}