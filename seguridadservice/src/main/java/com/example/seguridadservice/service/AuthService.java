package com.example.seguridadservice.service;

import com.example.seguridadservice.dto.LoginRequestDTO;
import com.example.seguridadservice.dto.RegisterRequestDTO;
import com.example.seguridadservice.dto.TokenResponseDTO;
import com.example.seguridadservice.model.UsuarioModel;
import com.example.seguridadservice.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Encriptador oficial de la PPT
import org.springframework.stereotype.Service;

@Service // 1. Define esta clase como el componente de lógica para la autenticación
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder; // 2. Herramienta para contrastar claves encriptadas

    // 3. Inyección de dependencias por constructor
    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDTO autenticar(LoginRequestDTO request) {
        // 4. Buscamos al usuario en MySQL. Si no existe, lanzamos un error inmediato
        UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Error: Credenciales inválidas (Usuario no encontrado)."));

        // 5. ¡PASO CRUCIAL!: Comparamos la clave cruda enviada contra la clave encriptada en la BD usando BCrypt
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Error: Credenciales inválidas (Contraseña incorrecta).");
        }

        // 6. Si pasa los filtros anteriores, significa que es real. Le fabricamos su Token JWT personalizado
        String tokenGenerado = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

        // 7. Retornamos el Token envuelto en el DTO estructurado
        return new TokenResponseDTO(tokenGenerado);
    }

    public TokenResponseDTO registrar(RegisterRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Error: El usuario ya se encuentra registrado.");
        }

        UsuarioModel nuevoUsuario = UsuarioModel.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol("ROLE_CLIENTE")
                .build();

        UsuarioModel guardado = usuarioRepository.save(nuevoUsuario);
        String tokenGenerado = jwtService.generarToken(guardado.getEmail(), guardado.getRol());

        return new TokenResponseDTO(tokenGenerado);
    }
}