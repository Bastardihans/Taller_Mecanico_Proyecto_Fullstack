package com.example.vehiculo.vehiculoservice.controller;

import com.example.vehiculo.vehiculoservice.dto.request.LoginRequestDTO;
import com.example.vehiculo.vehiculoservice.dto.response.LoginResponseDTO;
import com.example.vehiculo.vehiculoservice.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        // Usuario quemado solo para la clase
        if (!request.getUsername().equals("admin") || !request.getPassword().equals("1234")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String token = jwtService.generarToken(request.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }
}
