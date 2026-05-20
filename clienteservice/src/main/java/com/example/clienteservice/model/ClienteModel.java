
package com.example.clienteservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    private String apellido;

    private String telefono;

    private String correo;

    private String direccion;
}

