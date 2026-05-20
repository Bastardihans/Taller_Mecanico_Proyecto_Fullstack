package com.example.clienteservice.dto.response;

import lombok.Data;

@Data
public class VehiculoDTO {

    private Long id;
    private String patente;
    private String marca;
    private String modelo;
}