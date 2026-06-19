
package com.example.ordentrabajo.ordentrabajoservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes_trabajo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenTrabajoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación lógica con VehiculoService
    @Column(nullable = false)
    private Long vehiculoId;

    // Relación lógica con MecanicoService
    @Column(nullable = false)
    private Long mecanicoId;

    // Relación lógica con ServicioService
    @Column
    private Long servicioId;

    @Column
    private Double costoRepuestos;

    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcionFalla;

    // PENDIENTE
    // EN_PROCESO
    // FINALIZADO
    // ENTREGADO
    @Column(nullable = false)
    private String estado;
}

