package com.example.seguridadservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.seguridadservice.model.UsuarioModel;
import com.example.seguridadservice.repository.UsuarioRepository;

@SpringBootApplication
public class SeguridadserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguridadserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedInitialUser(UsuarioRepository usuarioRepository) {
		return args -> {
			String adminEmail = "admin@taller.com";
			if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
				String password = new BCryptPasswordEncoder().encode("Admin123!");
				UsuarioModel admin = UsuarioModel.builder()
					.email(adminEmail)
					.password(password)
					.rol("ROLE_ADMIN")
					.build();
				usuarioRepository.save(admin);
			}
		};
	}

}
