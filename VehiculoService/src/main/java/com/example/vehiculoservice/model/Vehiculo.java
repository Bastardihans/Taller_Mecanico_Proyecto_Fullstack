package com.example.vehiculoservice.model;

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
@Table(name = "vehiculos") // Define el nombre de la tabla en MySQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria autoincremental
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId; // ID que hace referencia al cliente en el otro microservicio

    @Column(unique = true, nullable = false, length = 10)
    private String patente;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer anio; // Usamos Integer (objeto) en vez de int para evitar conflictos de nulos

    @Column(name = "en_reparacion", nullable = false)
    private Boolean enReparacion; // Usamos Boolean objeto para mapeos limpios con Lombok
}