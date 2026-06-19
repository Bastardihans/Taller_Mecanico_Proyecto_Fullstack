package com.example.facturacionservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FacturaNotFoundException extends ResponseStatusException {
    public FacturaNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Factura no encontrada con id: " + id);
    }
}
