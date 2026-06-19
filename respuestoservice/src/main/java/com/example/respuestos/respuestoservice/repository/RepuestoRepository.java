package com.example.respuestos.respuestoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.respuestos.respuestoservice.model.RepuestoModel;

@Repository
public interface RepuestoRepository extends JpaRepository<RepuestoModel, Long> {
}
