package com.example.agendaservice.model;

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

@Entity                        // 1. Le dice a Hibernate: "Traduce esta clase a una tabla de MySQL"
@Table(name = "agendas")       // 2. Le da el nombre físico a la tabla en la base de datos
@Data                          // 3. De Lombok: Genera en silencio todos los Getters y Setters
@NoArgsConstructor             // 4. De Lombok: Crea un constructor vacío obligatorio para Hibernate
@AllArgsConstructor            // 5. De Lombok: Crea un constructor con todos los atributos
@Builder                       // 6. Nos ayuda a crear instancias de esta clase de forma limpia
public class AgendaModel {

    @Id                        // 7. Define que este atributo es la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 8. ¡AQUÍ ESTÁ EL AUTOINCREMENTABLE! MySQL sumará 1 automáticamente
    private Long id;

    // Relación lógica: Guardamos solo el ID del cliente que está en el servicio de Hans
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String motivo;

    // Estados posibles: PENDIENTE, CONFIRMADA, CANCELADA, FINALIZADA
    @Column(nullable = false)
    private String estado; 
}