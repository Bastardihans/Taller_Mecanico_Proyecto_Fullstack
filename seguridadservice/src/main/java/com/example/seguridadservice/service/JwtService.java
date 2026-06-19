package com.example.seguridadservice.service;

import io.jsonwebtoken.Jwts; // Herramientas de generación de JWT
import io.jsonwebtoken.SignatureAlgorithm; // Algoritmo de firmado criptográfico
import io.jsonwebtoken.security.Keys; // Herramienta para manejar llaves secretas de cifrado
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service // 1. Registra esta clase como componente especializado de lógica de tokens
public class JwtService {

    // 2. Creamos una llave secreta segura y aleatoria para firmar digitalmente nuestros tokens
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generarToken(String email, String rol) {
        long tiempoExpiracion = 86400000; // 3. El token durará exactamente 24 horas (en milisegundos)

        // 4. Construimos el JSON Web Token estructurado según la especificación de la PPT
        return Jwts.builder()
                .setSubject(email) // 5. Guardamos la identidad del usuario (Subject)
                .claim("role", rol) // 6. Inyectamos un dato personalizado (Claim): Su rol jerárquico
                .setIssuedAt(new Date(System.currentTimeMillis())) // 7. Registramos la fecha exacta de creación
                .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion)) // 8. Registramos cuándo caduca
                .signWith(SECRET_KEY) // 9. Firmamos criptográficamente el token completo con nuestra llave secreta
                .compact(); // 10. Serializa todo a una cadena de texto compactada (un string largo)
    }
}