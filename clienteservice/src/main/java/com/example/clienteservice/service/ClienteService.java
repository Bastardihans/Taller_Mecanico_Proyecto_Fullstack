package com.example.clienteservice.service;




import com.example.clienteservice.dto.request.ClienteRequestDTO;
import com.example.clienteservice.dto.response.ClienteResponseDTO;
import com.example.clienteservice.model.ClienteModel;
import com.example.clienteservice.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
   

    // Obtener todos los clientes
    public List<ClienteModel> obtenerTodos() {
        return clienteRepository.findAll();
    }

    // Obtener cliente por ID
    public ClienteModel obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado con id: " + id));
    }

    // Crear cliente
    public ClienteResponseDTO guardar(ClienteRequestDTO request) {

        if (clienteRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El rut ya existe");
        }

        ClienteModel cliente = ClienteModel.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .direccion(request.getDireccion())
                .build();

        ClienteModel guardado = clienteRepository.save(cliente);

        return ClienteResponseDTO.builder()
                .id(guardado.getId())
                .rut(guardado.getRut())
                .nombre(guardado.getNombre())
                .apellido(guardado.getApellido())
                .telefono(guardado.getTelefono())
                .correo(guardado.getCorreo())
                .direccion(guardado.getDireccion())
                .build();
    }

    // Actualizar cliente
    public ClienteModel actualizar(Long id, ClienteRequestDTO request) {

        ClienteModel existente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        existente.setRut(request.getRut());
        existente.setNombre(request.getNombre());
        existente.setApellido(request.getApellido());
        existente.setTelefono(request.getTelefono());
        existente.setCorreo(request.getCorreo());
        existente.setDireccion(request.getDireccion());

        return clienteRepository.save(existente);
    }

    // Eliminar cliente
    public boolean eliminar(Long id) {

        if (!clienteRepository.existsById(id)) {
            return false;
        }

        clienteRepository.deleteById(id);
        return true;
    }

   
   
    public ClienteResponseDTO obtenerPorRut(String rut) {

    ClienteModel cliente = clienteRepository.findByRut(rut)
            .orElseThrow(() ->
                    new RuntimeException("Cliente no encontrado"));

    return ClienteResponseDTO.builder()
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .rut(cliente.getRut())
            .correo(cliente.getCorreo())
            .telefono(cliente.getTelefono())
            .build();
}


}

