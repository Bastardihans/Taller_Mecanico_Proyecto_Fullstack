package com.example.mecanico.mecanicoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mecanicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class MecanicoModel {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String rut;

    private String nombre;

    private String especialidad;

    private String telefono;

    private boolean disponible;
}



