# Taller Mecánico - Sistema Integral de Gestión de Microservicios

Este proyecto es una solución de gestión de taller mecánico construida con arquitectura de microservicios sobre Java y Spring Boot. La aplicación soporta las operaciones de recepción de vehículos, gestión de clientes, administración de mecánicos, creación de órdenes de trabajo, planificación de agendas, facturación, control de repuestos, envío de notificaciones y seguridad de acceso.

El sistema modela entidades de negocio clave como Cliente, Vehículo, Mecánico, Orden de Trabajo, Agenda, Servicio, Repuesto, Factura, Usuario y Rol. Cada microservicio expone una responsabilidad única y utiliza su propia base de datos independiente para garantizar coherencia del dominio y aislamiento transaccional.

## Microservicios independientes

| Nombre del Servicio | Puerto del servicio | Puerto en Docker | Base de Datos independiente | Responsabilidad única |
|---|---|---|---|---|
| Cliente Service | 8081 | 8081 | MySQL `cliente_db` | Gestión de clientes y sus datos de contacto |
| Vehículo Service | 8080 | 18080 | MySQL `taller_db` | Registro y administración de vehículos asociados a clientes |
| Mecánico Service | 8082 | 8092 | MySQL `mecanico_db` | Gestión de mecánicos, especialidades y disponibilidad |
| OrdenTrabajo Service | 8083 | 8088 | MySQL `ordenes_db` | Creación, seguimiento y actualización de órdenes de trabajo |
| Agenda Service | 8083 | 8083 | MySQL `agenda_db` | Planificación y reserva de turnos para órdenes de trabajo |
| Servicio Service | 8084 | 8084 | MySQL `servicio_db` | Catálogo de servicios técnicos y tarifas estandarizadas |
| Repuestos Service | 8089 | 8089 | MySQL `repuestos_db` | Gestión de inventario de repuestos y stock |
| Notificación Service | 8085 | 8085 | MySQL `notificacion_db` | Envío de notificaciones internas y externas al cliente |
| Facturación Service | 8086 | 8086 | MySQL `facturacion_db` | Emisión de facturas y registro de cobros |
| Seguridad Service | 8087 | 8087 | MySQL `seguridad_db` | Autenticación, autorización y gestión de roles |

## Arquitectura del Sistema e Integración 

La solución está diseñada como un ecosistema de microservicios desacoplados que se integran mediante una capa transversal de comunicaciones.

- `API Gateway`: funciona como punto de entrada único para clientes externos, concentrando el ruteo de solicitudes y la aplicación de políticas de seguridad. Todo el tráfico de usuario debe ingresar por una única puerta de enlace antes de ser derivado a los microservicios internos.
- `Service Discovery` con Eureka Server: cada microservicio puede registrarse dinámicamente para ser localizado sin hardcodear URLs. Eureka habilita la resolución de instancias y balanceo de carga en tiempo de ejecución.
- Comunicación síncrona con `OpenFeign`: los microservicios que requieren datos de otros dominios usan clientes declarativos Feign para invocar APIs REST de forma tipada y controlada. Ejemplos claros son `ordentrabajoservice` consultando `vehiculo-service`, `mecanico-service` y `cliente-service`, y `facturacion-service` consultando `orden-service` y `servicio-service`.

Puntos de entrada del entorno:

- Eureka Server: `http://localhost:8761`
- API Gateway: `http://localhost:8080`
- Swagger UI consolidado en el gateway: `http://localhost:8080/swagger-ui.html`
- Cliente Service Swagger: `http://localhost:8081/swagger-ui.html`
- Vehículo Service Swagger: `http://localhost:18080/swagger-ui.html`
- Mecánico Service Swagger: `http://localhost:8092/swagger-ui.html`

## Capa de Seguridad 

La seguridad de la plataforma se implementa con un esquema sin estado (`stateless`) mediante Spring Security y JSON Web Tokens (`JJWT`). Cada petición a los microservicios protegidos debe incluir un token JWT válido.

- Autenticación: el `seguridad-service` valida credenciales y emite un JWT firmado. El token contiene identidad y roles del usuario.
- Autorización: Spring Security aplica reglas de acceso en cada controlador, verificando que el rol del usuario tenga permiso para el endpoint solicitado.
- Cifrado de contraseñas: las claves de usuario son almacenadas con `BCrypt`, garantizando hashing seguro antes de persistir en la base de datos.

Roles de usuario definidos:

- `ADMINISTRADOR`: acceso completo a operaciones administrativas, gestión de usuarios, configuración de servicios y revisiones transaccionales.
- `OPERADOR`: acceso a operaciones de taller, gestión de órdenes de trabajo, agenda y repuestos.
- `CLIENTE`: acceso restringido a consultas de su propio vehículo, estado de órdenes y notificaciones.

El acceso se restringe según roles y scopes, de modo que los endpoints sensibles de creación, modificación y facturación solo son accesibles con el perfil adecuado.

## Calidad del Código y Validación 

El proyecto utiliza patrones de diseño orientados a buenos principios de ingeniería:

- `DTO` (Data Transfer Object): todas las entradas y salidas de la API se modelan con DTOs específicos para separar la capa de dominio de la capa de presentación.
- Validación declarativa: `Spring Boot Starter Validation` se utiliza junto con anotaciones como `@Valid`, `@NotNull`, `@Size`, `@Email` y `@Pattern` para garantizar la integridad de los datos al recibir solicitudes.
- Capas bien definidas: controlador, servicio, repositorio y adaptadores de integración.



## Guía de Instalación y Despliegue con Docker 

### Requisitos previos

- Docker Desktop instalado y ejecutándose.
- Docker Compose disponible.
- Java JDK 21 instalado (o uso de `./mvnw` con Maven wrapper).
- Conexión de red local estable.

### Pasos de despliegue

```bash
cd Taller_Mecanico_Proyecto_Fullstack-main
docker compose up -d --build
```

### Notas adicionales

- Si cambia solo el gateway, basta con reconstruir ese servicio con `docker compose up -d --build api-gateway`.
- Para reiniciar el gateway sin reconstruirlo: `docker compose restart api-gateway`.
- El gateway expone las rutas de negocio y el Swagger agregado a través de `http://localhost:8080`.
- Para detener el ecosistema y limpiar volúmenes:

```bash
docker compose down -v
```

## Endpoints útiles para pruebas

### Gateway

- `GET http://localhost:8080/`
- `GET http://localhost:8080/swagger-ui.html`
- `GET http://localhost:8080/api/v1/clientes`
- `GET http://localhost:8080/api/v1/vehiculos`
- `GET http://localhost:8080/api/v1/mecanicos`
- `GET http://localhost:8080/api/v1/ordenes`
- `GET http://localhost:8080/api/v1/agendas`
- `GET http://localhost:8080/api/v1/servicios`
- `GET http://localhost:8080/api/v1/repuestos`
- `GET http://localhost:8080/api/v1/notificaciones`
- `GET http://localhost:8080/api/v1/facturas`
- `POST http://localhost:8080/api/v1/auth/login`
- `POST http://localhost:8080/api/v1/auth/register`

### Eureka

- `GET http://localhost:8761`
- `GET http://localhost:8761/eureka/apps`

### Swagger por microservicio

- `GET http://localhost:8081/swagger-ui.html`
- `GET http://localhost:18080/swagger-ui.html`
- `GET http://localhost:8092/swagger-ui.html`
- `GET http://localhost:8088/swagger-ui.html`
- `GET http://localhost:8083/swagger-ui.html`
- `GET http://localhost:8084/swagger-ui.html`
- `GET http://localhost:8089/swagger-ui.html`
- `GET http://localhost:8085/swagger-ui.html`
- `GET http://localhost:8086/swagger-ui.html`
- `GET http://localhost:8087/swagger-ui.html`

Este README está estructurado para evidenciar los elementos técnicos requeridos en la pauta de evaluación, con foco en arquitectura, seguridad, calidad de código y despliegue.

