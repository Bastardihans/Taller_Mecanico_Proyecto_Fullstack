package com.example.facturacionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // ⬅️ Asegúrate de que este import exista

@SpringBootApplication
@EnableFeignClients // ⬅️ ¡ESTA ES LA ANOTACIÓN CLAVE QUE LE FALTA!
public class FacturacionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacturacionserviceApplication.class, args);
    }
}