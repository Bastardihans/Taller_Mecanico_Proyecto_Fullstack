package com.example.agendaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // 1. Importamos la herramienta de comunicación

@SpringBootApplication // 2. Indica que esta es la clase de configuración e inicio de Spring Boot
@EnableFeignClients    // 3. ¡CLAVE! Activa el escaneo y uso de clientes Feign en este microservicio
public class AgendaserviceApplication {

	public static void main(String[] args) {
		// 4. Arranca toda la maquinaria interna de Spring Boot
		SpringApplication.run(AgendaserviceApplication.class, args);
	}
}