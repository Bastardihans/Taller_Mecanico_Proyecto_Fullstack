package com.example.servicioservice.service;

import java.util.List;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service; 

import com.example.servicioservice.dto.ServicioRequestDTO;
import com.example.servicioservice.dto.ServicioResponseDTO;
import com.example.servicioservice.model.ServicioModel; 
import com.example.servicioservice.repository.ServicioRepository; 

@Service // 1. Le avisa a Spring que aquí se programa la lógica de negocio del catálogo
public class ServicioService {

    private final ServicioRepository servicioRepository; // 2. Declaramos la herramienta para hablar con MySQL

    // 3. Constructor para que Spring inyecte el repositorio automáticamente (Inyección de Dependencias)
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    // Método para crear un nuevo servicio en el catálogo
    public ServicioResponseDTO guardarServicio(ServicioRequestDTO request) {
        // 4. Transformamos el DTO de entrada en un Modelo de Base de Datos usando Builder de Lombok
        ServicioModel nuevoServicio = ServicioModel.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precioBase(request.getPrecioBase())
                .build();

        // 5. Guardamos en la base de datos y capturamos el objeto resultante (que ya incluye el ID autoincrementado)
        ServicioModel guardado = servicioRepository.save(nuevoServicio);

        // 6. Convertimos el Modelo guardado en un ResponseDTO limpio para retornar
        return new ServicioResponseDTO(guardado.getId(), guardado.getNombre(), guardado.getDescripcion(), guardado.getPrecioBase());
    }

    // Método para listar todo el catálogo de precios del taller
    public List<ServicioResponseDTO> obtenerTodosLosServicios() {
        // 7. Buscamos todos los registros en MySQL usando .findAll()
        List<ServicioModel> listaModelos = servicioRepository.findAll();

        // 8. Convertimos toda la lista de Modelos a una lista de ResponseDTOs usando Streams de Java
        return listaModelos.stream()
                .map(modelo -> new ServicioResponseDTO(modelo.getId(), modelo.getNombre(), modelo.getDescripcion(), modelo.getPrecioBase()))
                .collect(Collectors.toList());
    }
}