package com.example.agendaservice.service;

import org.springframework.stereotype.Service; 

import com.example.agendaservice.client.ClienteClient; // 1. Importamos el teléfono virtual de Feign
import com.example.agendaservice.dto.AgendaRequestDTO;
import com.example.agendaservice.dto.AgendaResponseDTO; 
import com.example.agendaservice.dto.ClienteResponseDTO;// 2. Importamos el DTO del cliente de Hans
import com.example.agendaservice.model.AgendaModel;
import com.example.agendaservice.repository.AgendaRepository;

@Service // 3. Le dice a Spring que esta clase contiene la lógica de negocio (El Cerebro)
public class AgendaService {

    private final AgendaRepository agendaRepository; // 4. Conexión a tu propia Base de Datos
    private final ClienteClient clienteClient;       // 5. Conexión externa al servicio de Hans

    // 6. Constructor para que Spring inyecte automáticamente ambas herramientas (Inyección de Dependencias)
    public AgendaService(AgendaRepository agendaRepository, ClienteClient clienteClient) {
        this.agendaRepository = agendaRepository;
        this.clienteClient = clienteClient;
    }

    public AgendaResponseDTO crearCita(AgendaRequestDTO request) {
        
        try {
            // 7. ¡LA LLAMADA CRUCIAL! Usamos Feign para ir a buscar al cliente en el servicio de Hans en tiempo real
            ClienteResponseDTO clienteEncontrado = clienteClient.obtenerClientePorId(request.getClienteId());
            
            // Si la línea de arriba no falla, significa que el cliente SÍ existe en el taller. Continuamos...
            
        } catch (Exception e) {
            // 8. Si Feign devuelve un error (ej: 404 de Hans), cae aquí y lanzamos una excepción propia
            throw new IllegalArgumentException("Error: El cliente con ID " + request.getClienteId() + " no existe en el sistema.");
        }

        // 9. Construimos el modelo de base de datos utilizando el patrón Builder
        AgendaModel nuevaAgenda = AgendaModel.builder()
                .clienteId(request.getClienteId()) // Guardamos la relación lógica (el ID)
                .fechaHora(request.getFechaHora())
                .motivo(request.getMotivo())
                .estado("PENDIENTE")               // Toda cita nueva inicia en estado Pendiente
                .build();

        // 10. Guardamos el registro de forma permanente en tu tabla 'agendas' de MySQL
        AgendaModel guardado = agendaRepository.save(nuevaAgenda);

        // 11. Devolvemos la respuesta formateada y limpia hacia el controlador
        return new AgendaResponseDTO(
                guardado.getId(),
                guardado.getClienteId(),
                guardado.getFechaHora(),
                guardado.getMotivo(),
                guardado.getEstado()
        );
    }
}