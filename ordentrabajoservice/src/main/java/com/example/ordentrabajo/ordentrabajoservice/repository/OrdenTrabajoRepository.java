
package com.example.ordentrabajo.ordentrabajoservice.repository;

import com.example.ordentrabajo.ordentrabajoservice.model.OrdenTrabajoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenTrabajoRepository
        extends JpaRepository<OrdenTrabajoModel, Long> {

    List<OrdenTrabajoModel> findByVehiculoId(Long vehiculoId);

    List<OrdenTrabajoModel> findByMecanicoId(Long mecanicoId);

    List<OrdenTrabajoModel> findByEstado(String estado);
}

