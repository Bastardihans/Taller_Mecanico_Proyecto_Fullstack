package com.example.clienteservice.repository;



import com.example.clienteservice.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    Optional<ClienteModel> findByRut(String rut);

    boolean existsByRut(String rut);
}

