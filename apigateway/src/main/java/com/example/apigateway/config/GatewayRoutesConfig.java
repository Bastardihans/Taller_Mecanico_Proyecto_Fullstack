package com.example.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder,
            @Value("${CLIENTE_SERVICE_URL:http://localhost:8081}") String clienteServiceUrl,
            @Value("${VEHICULO_SERVICE_URL:http://localhost:18080}") String vehiculoServiceUrl,
            @Value("${MECANICO_SERVICE_URL:http://localhost:8082}") String mecanicoServiceUrl,
            @Value("${ORDENTRABAJO_SERVICE_URL:http://localhost:8083}") String ordenTrabajoServiceUrl,
            @Value("${AGENDA_SERVICE_URL:http://localhost:8083}") String agendaServiceUrl,
            @Value("${SERVICIO_SERVICE_URL:http://localhost:8084}") String servicioServiceUrl,
            @Value("${REPUESTO_SERVICE_URL:http://localhost:8089}") String repuestoServiceUrl,
            @Value("${NOTIFICACION_SERVICE_URL:http://localhost:8085}") String notificacionServiceUrl,
            @Value("${FACTURACION_SERVICE_URL:http://localhost:8086}") String facturacionServiceUrl,
            @Value("${SEGURIDAD_SERVICE_URL:http://localhost:8087}") String seguridadServiceUrl) {

        return builder.routes()
                .route("cliente-service", route -> route
                        .path("/api/v1/clientes", "/api/v1/clientes/**")
                        .uri(clienteServiceUrl))
                .route("cliente-service-openapi", route -> route
                        .path("/api-docs/clienteservice")
                        .filters(filters -> filters.rewritePath("/api-docs/clienteservice", "/v3/api-docs"))
                        .uri(clienteServiceUrl))
                .route("vehiculo-service", route -> route
                        .path("/api/v1/vehiculos", "/api/v1/vehiculos/**")
                        .uri(vehiculoServiceUrl))
                .route("vehiculo-service-openapi", route -> route
                        .path("/api-docs/vehiculo-service")
                        .filters(filters -> filters.rewritePath("/api-docs/vehiculo-service", "/v3/api-docs"))
                        .uri(vehiculoServiceUrl))
                .route("mecanico-service", route -> route
                        .path("/api/v1/mecanicos", "/api/v1/mecanicos/**")
                        .uri(mecanicoServiceUrl))
                .route("mecanico-service-openapi", route -> route
                        .path("/api-docs/mecanico-service")
                        .filters(filters -> filters.rewritePath("/api-docs/mecanico-service", "/v3/api-docs"))
                        .uri(mecanicoServiceUrl))
                .route("orden-trabajo-service", route -> route
                        .path("/api/v1/ordenes", "/api/v1/ordenes/**")
                        .uri(ordenTrabajoServiceUrl))
                .route("orden-trabajo-service-openapi", route -> route
                        .path("/api-docs/ordentrabajoservice")
                        .filters(filters -> filters.rewritePath("/api-docs/ordentrabajoservice", "/v3/api-docs"))
                        .uri(ordenTrabajoServiceUrl))
                .route("agenda-service", route -> route
                        .path("/api/v1/agendas", "/api/v1/agendas/**")
                        .uri(agendaServiceUrl))
                .route("agenda-service-openapi", route -> route
                        .path("/api-docs/agenda-service")
                        .filters(filters -> filters.rewritePath("/api-docs/agenda-service", "/v3/api-docs"))
                        .uri(agendaServiceUrl))
                .route("servicio-service", route -> route
                        .path("/api/v1/servicios", "/api/v1/servicios/**")
                        .uri(servicioServiceUrl))
                .route("servicio-service-openapi", route -> route
                        .path("/api-docs/servicio-service")
                        .filters(filters -> filters.rewritePath("/api-docs/servicio-service", "/v3/api-docs"))
                        .uri(servicioServiceUrl))
                .route("repuesto-service", route -> route
                        .path("/api/v1/repuestos", "/api/v1/repuestos/**")
                        .uri(repuestoServiceUrl))
                .route("repuesto-service-openapi", route -> route
                        .path("/api-docs/respuestoservice")
                        .filters(filters -> filters.rewritePath("/api-docs/respuestoservice", "/v3/api-docs"))
                        .uri(repuestoServiceUrl))
                .route("notificacion-service", route -> route
                        .path("/api/v1/notificaciones", "/api/v1/notificaciones/**")
                        .uri(notificacionServiceUrl))
                .route("notificacion-service-openapi", route -> route
                        .path("/api-docs/notificacion-service")
                        .filters(filters -> filters.rewritePath("/api-docs/notificacion-service", "/v3/api-docs"))
                        .uri(notificacionServiceUrl))
                .route("facturacion-service", route -> route
                        .path("/api/v1/facturas", "/api/v1/facturas/**")
                        .uri(facturacionServiceUrl))
                .route("facturacion-service-openapi", route -> route
                        .path("/api-docs/facturacion-service")
                        .filters(filters -> filters.rewritePath("/api-docs/facturacion-service", "/v3/api-docs"))
                        .uri(facturacionServiceUrl))
                .route("seguridad-service", route -> route
                        .path("/api/v1/auth", "/api/v1/auth/**")
                        .uri(seguridadServiceUrl))
                .route("seguridad-service-openapi", route -> route
                        .path("/api-docs/seguridad-service")
                        .filters(filters -> filters.rewritePath("/api-docs/seguridad-service", "/v3/api-docs"))
                        .uri(seguridadServiceUrl))
                .build();
    }
}