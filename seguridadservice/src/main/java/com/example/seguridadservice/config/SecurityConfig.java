package com.example.seguridadservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 1. Le avisa a Spring Boot que esta es una clase de configuraciones del sistema
@EnableWebSecurity // 2. Activa el soporte de seguridad web personalizado de Spring Security
public class SecurityConfig {

    @Bean // 3. Registra este método como un Bean de configuración en el núcleo de Spring
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 4. Desactiva protección CSRF ya que usamos tokens JWT (Stateless)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll() // 5. ¡Abre las puertas de par en par para login y registro!
                .anyRequest().authenticated() // 6. Cualquier otra ruta interna del taller exigirá estar autenticado
            );
            
        return http.build(); // 7. Construye y retorna la cadena de filtros configurada
    }
}