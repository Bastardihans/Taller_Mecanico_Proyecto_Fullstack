package com.example.mecanico.mecanicoservice.repository;

import com.example.mecanico.mecanicoservice.model.MecanicoModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MecanicoRepository extends JpaRepository<MecanicoModel, Long> {

    Optional<MecanicoModel> findByRut(String rut);

    boolean existsByRut(String rut);
}