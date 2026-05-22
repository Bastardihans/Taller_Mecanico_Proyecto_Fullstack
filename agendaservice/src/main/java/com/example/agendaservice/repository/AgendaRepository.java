package com.example.agendaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.agendaservice.model.AgendaModel;

@Repository // Le avisa a Spring que este componente maneja el acceso al disco duro (Base de Datos)
public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
    // Heredando de JpaRepository, Spring implementa automáticamente:
    // .save(), .findById(), .findAll(), .deleteById() sin escribir una sola línea de código SQL.
}