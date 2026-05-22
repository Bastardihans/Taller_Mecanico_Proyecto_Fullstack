package com.example.notificacionservice.service;

import java.time.LocalDateTime; // Importa el DTO de entrada

import org.springframework.stereotype.Service; // Importa el DTO de salida

import com.example.notificacionservice.dto.NotificacionRequestDTO; // Importa el modelo de la BD
import com.example.notificacionservice.dto.NotificacionResponseDTO; // Importa la interfaz de acceso a datos
import com.example.notificacionservice.model.NotificacionModel; // Anotación que define la capa de lógica empresarial
import com.example.notificacionservice.repository.NotificacionRepository; // Permite capturar el tiempo del sistema

@Service // 1. Registra esta clase como el cerebro de lógica del microservicio de notificaciones
public class NotificacionService {

    private final NotificacionRepository notificacionRepository; // 2. Inyección de la herramienta de BD

    // 3. Constructor que permite la Inyección de Dependencias automática gestionada por Spring
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // Método encargado de registrar una nueva alerta en el historial del taller
    public NotificacionResponseDTO registrarNotificacion(NotificacionRequestDTO request) {
        
        // 4. Mapeamos los datos del RequestDTO a la Entidad utilizando el patrón Builder
        NotificacionModel nuevaNotificacion = NotificacionModel.builder()
                .clienteId(request.getClienteId()) // Asocia la alerta al ID lógico del cliente
                .medio(request.getMedio().toUpperCase()) // Normaliza el medio de envío en mayúsculas
                .mensaje(request.getMensaje())
                .fechaEnvio(LocalDateTime.now()) // 5. Captura el año, mes, día, hora y segundo exacto actual del servidor
                .build();

        // 6. Almacena el objeto en MySQL llamando al repositorio y captura el resultado con su ID autoincrementado
        NotificacionModel guardado = notificacionRepository.save(nuevaNotificacion);

        // 7. Retorna un DTO de respuesta limpio y desacoplado de la base de datos
        return new NotificacionResponseDTO(
                guardado.getId(),
                guardado.getClienteId(),
                guardado.getMedio(),
                guardado.getMensaje(),
                guardado.getFechaEnvio()
        );
    }
}