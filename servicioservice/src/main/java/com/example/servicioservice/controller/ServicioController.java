package com.example.servicioservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // Habilita la revisión de las validaciones del DTO (@NotBlank, @Positive)
import org.springframework.web.bind.annotation.PostMapping; // Importa los códigos de estado HTTP
import org.springframework.web.bind.annotation.RequestBody; // Importa la clase de control total HTTP de la PPT
import org.springframework.web.bind.annotation.RequestMapping; // Importa las anotaciones de las rutas REST
import org.springframework.web.bind.annotation.RestController;

import com.example.servicioservice.dto.ServicioRequestDTO;
import com.example.servicioservice.dto.ServicioResponseDTO;
import com.example.servicioservice.service.ServicioService;

import jakarta.validation.Valid;

@RestController // 1. Le dice a Spring que esta clase es una API REST que devuelve respuestas en formato JSON
@RequestMapping("/api/v1/servicios") // 2. URL base de este microservicio
public class ServicioController {

    private final ServicioService servicioService; // 3. Conectamos con el cerebro del servicio

    // 4. Inyección del servicio por constructor
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping // 5. Atrapa las peticiones HTTP POST dirigidas a /api/v1/servicios (Crear)
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioRequestDTO request) {
        // @Valid: Valida el formulario antes de procesarlo. Si falla, corta el flujo y lanza un 400 Bad Request
        // @RequestBody: Captura el JSON enviado por internet y lo convierte en el objeto 'request'
        
        ServicioResponseDTO respuesta = servicioService.guardarServicio(request);
        
        // 6. Respondemos usando ResponseEntity con el código semántico 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping // 7. Atrapa las peticiones HTTP GET dirigidas a /api/v1/servicios (Listar todo)
    public ResponseEntity<List<ServicioResponseDTO>> listarServicios() {
        List<ServicioResponseDTO> lista = servicioService.obtenerTodosLosServicios();
        
        // 8. Respondemos con un código 200 OK porque es una consulta de lectura exitosa
        return ResponseEntity.ok(lista);
    }
}