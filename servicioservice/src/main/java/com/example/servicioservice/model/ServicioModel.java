package com.example.servicioservice.model;

import jakarta.persistence.Column; // Importa las anotaciones del estándar JPA
import jakarta.persistence.Entity; // Importa las herramientas de ahorro de código de Lombok
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 1. Le dice a Hibernate que esta clase se transformará en una tabla de MySQL
@Table(name = "servicios_taller") // 2. Define el nombre físico de la tabla en MySQL
@Data // 3. Lombok genera automáticamente los Getters, Setters y toString()
@NoArgsConstructor // 4. Lombok genera el constructor vacío requerido por JPA
@AllArgsConstructor // 5. Lombok genera el constructor con todos los campos
@Builder // 6. Nos permite construir objetos de esta clase de forma limpia
public class ServicioModel {

    @Id // 7. Define este campo como la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 8. Le dice a MySQL que el ID es Autoincrementable (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false) // 9. Restricción en BD: El nombre debe ser ÚNICO y no puede ser nulo
    private String nombre;

    @Column(nullable = false) // 10. Restricción en BD: La descripción es obligatoria
    private String descripcion;

    @Column(nullable = false) // 11. Restricción en BD: El precio base no puede estar vacío
    private Double precioBase;
}