package com.example.seguridadservice.model;

import jakarta.persistence.*; // Importa los estándares de JPA
import lombok.*; // Importa Lombok para ahorro de código

@Entity // 1. Convierte esta clase en una tabla de base de datos relacional para Hibernate
@Table(name = "usuarios_sistema") // 2. Define el nombre físico de la tabla en MySQL
@Data // 3. Genera automáticamente los Getters, Setters y toString()
@NoArgsConstructor // 4. Constructor vacío obligatorio para que JPA instancie los datos
@AllArgsConstructor // 5. Constructor completo
@Builder // 6. Permite construir instancias de usuarios de forma limpia
public class UsuarioModel {

    @Id // 7. Define este campo como la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 8. Autoincremento gestionado directamente por MySQL
    private Long id;

    @Column(unique = true, nullable = false) // 9. El correo electrónico será el identificador de inicio de sesión (Único)
    private String email;

    @Column(nullable = false) // 10. Almacenará la contraseña cifrada (¡NUNCA texto plano!) mediante BCrypt
    private String password;

    @Column(nullable = false) // 11. Define el rol jerárquico dentro del taller (ej: "ROLE_ADMIN", "ROLE_MECANICO")
    private String rol;
}