package com.example.clienteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class ClienteserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteserviceApplication.class, args);
	}

}
