package com.example.seguridadservice.controller;

import com.example.seguridadservice.dto.LoginRequestDTO;
import com.example.seguridadservice.dto.RegisterRequestDTO;
import com.example.seguridadservice.dto.TokenResponseDTO;
import com.example.seguridadservice.service.AuthService;
import jakarta.validation.Valid; // Activa validaciones del Bean Validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Contenedor de respuestas completas HTTP de la PPT
import org.springframework.web.bind.annotation.*;

@RestController // 1. Indica que es una API REST mapeada a JSON automático
@RequestMapping("/api/v1/auth") // 2. URL raíz pública de autenticación
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login") // 3. Endpoint final queda mapeado como: POST /api/v1/auth/login
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        
        TokenResponseDTO respuestaToken = authService.autenticar(request);
        
        // 4. Respondemos con un código HTTP 200 OK adjuntando el token JWT generado
        return ResponseEntity.ok(respuestaToken);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        TokenResponseDTO respuestaToken = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaToken);
    }
}