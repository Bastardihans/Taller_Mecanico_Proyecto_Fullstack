package com.example.seguridadservice.repository;

import com.example.seguridadservice.model.UsuarioModel; // Carga el modelo de usuario
import org.springframework.data.jpa.repository.JpaRepository; // Interfaz genérica de persistencia de Spring Data
import org.springframework.stereotype.Repository;

import java.util.Optional; // Contenedor para manejar valores que pueden ser nulos de forma segura

@Repository // 1. Registra este componente en el contenedor de inversión de control de Spring
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    
    // 2. Query Derivada automática: Spring escribe el SQL para buscar un usuario por su Email en MySQL
    Optional<UsuarioModel> findByEmail(String email);
}