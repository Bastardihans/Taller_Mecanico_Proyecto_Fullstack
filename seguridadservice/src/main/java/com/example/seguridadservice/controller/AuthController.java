package com.example.seguridadservice.controller;

import com.example.seguridadservice.dto.LoginRequestDTO;
import com.example.seguridadservice.dto.RegisterRequestDTO;
import com.example.seguridadservice.dto.TokenResponseDTO;
import com.example.seguridadservice.service.AuthService;
import jakarta.validation.Valid; // Activa validaciones del Bean Validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Contenedor de respuestas completas HTTP de la PPT
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController // 1. Indica que es una API REST mapeada a JSON automático
@RequestMapping("/api/v1/auth") // 2. URL raíz pública de autenticación
@Tag(name = "Autenticación", description = "Inicio de sesión y registro de usuarios con JWT.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login") // 3. Endpoint final queda mapeado como: POST /api/v1/auth/login
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDTO.class), examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOi...\",\"tipo\":\"Bearer\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales de acceso", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class), examples = @ExampleObject(value = "{\"email\":\"admin@mail.com\",\"password\":\"123456\"}")))
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        
        TokenResponseDTO respuestaToken = authService.autenticar(request);
        
        // 4. Respondemos con un código HTTP 200 OK adjuntando el token JWT generado
        return ResponseEntity.ok(respuestaToken);
    }

    @PostMapping("/register")
        @Operation(summary = "Registrar usuario", description = "Crea un usuario nuevo y devuelve el token JWT.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDTO.class), examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOi...\",\"tipo\":\"Bearer\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a registrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterRequestDTO.class), examples = @ExampleObject(value = "{\"email\":\"admin@mail.com\",\"password\":\"123456\"}")))
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        TokenResponseDTO respuestaToken = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaToken);
    }
}