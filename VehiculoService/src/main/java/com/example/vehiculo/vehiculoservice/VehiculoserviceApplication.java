package com.example.vehiculo.vehiculoservice;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class VehiculoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculoserviceApplication.class, args);
	}

}
