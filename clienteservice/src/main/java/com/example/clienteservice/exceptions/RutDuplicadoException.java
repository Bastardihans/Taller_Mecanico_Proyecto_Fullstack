package com.example.clienteservice.exceptions;

public class RutDuplicadoException extends RuntimeException {

    public RutDuplicadoException(String message) {
        super(message);
    }
}