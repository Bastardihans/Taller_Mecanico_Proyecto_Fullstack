package com.example.notificacionservice.model;

import java.time.LocalDateTime; // Importa las anotaciones del estándar JPA para mapear la base de datos

import jakarta.persistence.Column; // Importa las herramientas de generación automática de código de Lombok
import jakarta.persistence.Entity; // Importa la clase de manejo de fecha y hora de Java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 1. Le dice a Hibernate que esta clase representa una tabla en nuestra base de datos relacional
@Table(name = "notificaciones") // 2. Define que la tabla física en MySQL se llamará "notificaciones"
@Data // 3. Lombok genera en segundo plano todos los Getters, Setters, equals, hashCode y toString
@NoArgsConstructor // 4. Genera el constructor sin argumentos requerido de manera obligatoria por JPA
@AllArgsConstructor // 5. Genera un constructor con todos los atributos de la clase
@Builder // 6. Implementa el patrón de diseño Builder para instanciar objetos de manera limpia
public class NotificacionModel {

    @Id // 7. Establece este atributo como la clave primaria (Primary Key) de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 8. Configura el ID como un campo Autoincrementable administrado por MySQL
    private Long id;

    @Column(name = "cliente_id", nullable = false) // 9. Relación lógica: Guarda el ID del cliente que vive en el servicio de Hans
    private Long clienteId;

    @Column(nullable = false) // 10. El canal por el cual se envía (ej: "CORREO", "SMS", "WHATSAPP")
    private String medio;

    @Column(nullable = false, length = 500) // 11. Define que el mensaje es obligatorio y tendrá un límite de 500 caracteres
    private String mensaje;

    @Column(nullable = false) // 12. Almacena el momento exacto en el que se generó la notificación
    private LocalDateTime fechaEnvio;
}