package com.example.facturacionservice.dto;

import jakarta.validation.constraints.NotBlank;

public class FacturaUpdateDTO {

    @NotBlank(message = "El estado de pago es obligatorio")
    private String estadoPago;

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
}
