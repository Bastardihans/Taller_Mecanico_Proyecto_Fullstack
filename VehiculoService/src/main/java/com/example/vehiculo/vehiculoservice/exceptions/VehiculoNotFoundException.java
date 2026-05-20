package com.example.vehiculo.vehiculoservice.exceptions;


public class VehiculoNotFoundException extends RuntimeException {

    public VehiculoNotFoundException(String mensaje) {
        super(mensaje);
    }
}