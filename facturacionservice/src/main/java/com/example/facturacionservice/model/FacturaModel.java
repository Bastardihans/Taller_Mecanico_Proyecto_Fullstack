package com.example.facturacionservice.model;

import java.time.LocalDateTime; // Importa la especificación estándar de persistencia Jakarta

import jakarta.persistence.Column; // Importa anotaciones de Lombok para limpiar el archivo
import jakarta.persistence.Entity; // Clase para manejar marcas de tiempo
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 1. Mapea esta clase de Java como una entidad de base de datos relacional para Hibernate
@Table(name = "facturas") // 2. Nombra la tabla física en MySQL como "facturas"
@Data // 3. Genera automáticamente en segundo plano Getters, Setters y toString()
@NoArgsConstructor // 4. Genera el constructor vacío obligatorio que exige JPA para operar
@AllArgsConstructor // 5. Genera el constructor con todos los atributos de la clase
@Builder // 6. Permite construir instancias de esta clase de manera fluida y elegante
public class FacturaModel {

    @Id // 7. Establece este campo como la Llave Primaria (PK) de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 8. Delega a MySQL el autoincremento secuencial (1, 2, 3...)
    private Long id;

    @Column(name = "orden_id", nullable = false, unique = true) // 9. Relación lógica: Una factura por cada Orden de Trabajo de Hans
    private Long ordenId;

    @Column(nullable = false) // 10. Almacena el cálculo total de la suma de repuestos y servicios
    private Double montoTotal;

    @Column(nullable = false) // 11. Estado del pago: "PENDIENTE" o "PAGADA"
    private String estadoPago;

    @Column(nullable = false) // 12. Fecha y hora exacta de emisión del cobro
    private LocalDateTime fechaEmision;
}