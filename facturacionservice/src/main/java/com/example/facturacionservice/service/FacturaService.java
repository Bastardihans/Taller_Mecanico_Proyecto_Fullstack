package com.example.facturacionservice.service;

import java.time.LocalDateTime; // Cliente Feign para hablar con Hans

import org.springframework.stereotype.Service; // Cliente Feign para hablar con tu Catálogo

import com.example.facturacionservice.client.OrdenClient;
import com.example.facturacionservice.client.ServicioClient;
import com.example.facturacionservice.dto.FacturaRequestDTO;
import com.example.facturacionservice.dto.FacturaResponseDTO;
import com.example.facturacionservice.dto.FacturaUpdateDTO;
import com.example.facturacionservice.dto.OrdenResponseDTO;
import com.example.facturacionservice.dto.ServicioResponseDTO;
import com.example.facturacionservice.exceptions.FacturaNotFoundException;
import com.example.facturacionservice.model.FacturaModel;
import com.example.facturacionservice.repository.FacturaRepository;

@Service // 1. Registra este componente como la capa de lógica de negocio financiera
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final OrdenClient ordenClient;
    private final ServicioClient servicioClient;

    // 2. Inyección por constructor de las 3 dependencias necesarias
    public FacturaService(FacturaRepository facturaRepository, OrdenClient ordenClient, ServicioClient servicioClient) {
        this.facturaRepository = facturaRepository;
        this.ordenClient = ordenClient;
        this.servicioClient = servicioClient;
    }

    public FacturaResponseDTO emitirFactura(FacturaRequestDTO request) {
        
        // 3. LLAMADA 1 (A Hans): Vamos a buscar la orden de trabajo para saber cuánto se gastó en repuestos
        OrdenResponseDTO orden = ordenClient.obtenerOrdenPorId(request.getOrdenId());
        Double costoRepuestos = orden.getCostoRepuestos();

        // 4. LLAMADA 2 (A tu servicio): Buscamos el precio base de la mano de obra según el servicio aplicado
        ServicioResponseDTO servicio = servicioClient.obtenerServicioPorId(orden.getServicioId());
        Double costoManoObra = servicio.getPrecioBase();

        // 5. LÓGICA DE NEGOCIO: Calculamos el monto total sumando ambos factores
        Double montoFinalCalculado = costoRepuestos + costoManoObra;

        // 6. MAPEADO A ENTIDAD: Construimos el registro para la Base de Datos
        FacturaModel nuevaFactura = FacturaModel.builder()
                .ordenId(request.getOrdenId())
                .montoTotal(montoFinalCalculado) // Guardamos el valor calculado matemáticamente
                .estadoPago("PENDIENTE") // Todo documento inicia sin pagar
                .fechaEmision(LocalDateTime.now()) // Captura el tiempo actual
                .build();

        // 7. PERSISTENCIA: Guardamos en MySQL usando Spring Data JPA
        FacturaModel guardada = facturaRepository.save(nuevaFactura);

        // 8. RETORNO: Entregamos el DTO de salida estructurado
        return new FacturaResponseDTO(
                guardada.getId(),
                guardada.getOrdenId(),
                guardada.getMontoTotal(),
                guardada.getEstadoPago(),
                guardada.getFechaEmision()
        );
    }

    public java.util.List<FacturaResponseDTO> obtenerTodas() {
        return facturaRepository.findAll().stream()
                .map(factura -> new FacturaResponseDTO(
                        factura.getId(),
                        factura.getOrdenId(),
                        factura.getMontoTotal(),
                        factura.getEstadoPago(),
                        factura.getFechaEmision()
                ))
                .toList();
    }

    public FacturaResponseDTO obtenerPorId(Long id) {
        FacturaModel factura = facturaRepository.findById(id)
                .orElseThrow(() -> new FacturaNotFoundException(id));

        return new FacturaResponseDTO(
                factura.getId(),
                factura.getOrdenId(),
                factura.getMontoTotal(),
                factura.getEstadoPago(),
                factura.getFechaEmision()
        );
    }

    public FacturaResponseDTO actualizarEstadoPago(Long id, FacturaUpdateDTO request) {
        FacturaModel existente = facturaRepository.findById(id)
                .orElseThrow(() -> new FacturaNotFoundException(id));

        existente.setEstadoPago(request.getEstadoPago());
        FacturaModel actualizado = facturaRepository.save(existente);

        return new FacturaResponseDTO(
                actualizado.getId(),
                actualizado.getOrdenId(),
                actualizado.getMontoTotal(),
                actualizado.getEstadoPago(),
                actualizado.getFechaEmision()
        );
    }

    public boolean eliminar(Long id) {
        if (!facturaRepository.existsById(id)) {
            return false;
        }
        facturaRepository.deleteById(id);
        return true;
    }
}