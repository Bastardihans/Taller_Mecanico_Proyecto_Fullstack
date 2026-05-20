package com.example.vehiculo.vehiculoservice.exceptions;

public class PatenteDuplicadaException extends RuntimeException {

    public PatenteDuplicadaException(String message) {
        super(message);
    }
}