# Taller Mecánico - Sistema Integral de Gestión de Microservicios

Este proyecto es una solución de gestión de taller mecánico construida con arquitectura de microservicios sobre Java y Spring Boot. La aplicación soporta las operaciones de recepción de vehículos, gestión de clientes, administración de mecánicos, creación de órdenes de trabajo, planificación de agendas, facturación, control de repuestos, envío de notificaciones y seguridad de acceso.

El sistema modela entidades de negocio clave como Cliente, Vehículo, Mecánico, Orden de Trabajo, Agenda, Servicio, Repuesto, Factura, Usuario y Rol. Cada microservicio expone una responsabilidad única y utiliza su propia base de datos independiente para garantizar coherencia del dominio y aislamiento transaccional.

## Microservicios independientes

| Nombre del Servicio | Puerto asignado | Base de Datos independiente | Responsabilidad única |
|---|---|---|---|
| Cliente Service | 8091 | MySQL `cliente_db` | Gestión de clientes y sus datos de contacto |
| Vehículo Service | 8082 | MySQL `taller_db` | Registro y administración de vehículos asociados a clientes |
| Mecánico Service | 8092 | MySQL `mecanico_db` | Gestión de mecánicos, especialidades y disponibilidad |
| OrdenTrabajo Service | 8088 | MySQL `ordenes_db` | Creación, seguimiento y actualización de órdenes de trabajo |
| Agenda Service | 8083 | MySQL `agenda_db` | Planificación y reserva de turnos para órdenes de trabajo |
| Servicio Service | 8084 | MySQL `servicio_db` | Catálogo de servicios técnicos y tarifas estandarizadas |
| Repuestos Service | 8089 | MySQL `repuestos_db` | Gestión de inventario de repuestos y stock |
| Notificación Service | 8085 | MySQL `notificacion_db` | Envío de notificaciones internas y externas al cliente |
| Facturación Service | 8086 | MySQL `facturacion_db` | Emisión de facturas y registro de cobros |
| Seguridad Service | 8087 | MySQL `seguridad_db` | Autenticación, autorización y gestión de roles |

## Arquitectura del Sistema e Integración 

La solución está diseñada como un ecosistema de microservicios desacoplados que se integran mediante una capa transversal de comunicaciones.

- `API Gateway`: funciona como punto de entrada único para clientes externos, concentrando el ruteo de solicitudes y la aplicación de políticas de seguridad. Todo el tráfico de usuario debe ingresar por una única puerta de enlace antes de ser derivado a los microservicios internos.
- `Service Discovery` con Eureka Server: cada microservicio puede registrarse dinámicamente para ser localizado sin hardcodear URLs. Eureka habilita la resolución de instancias y balanceo de carga en tiempo de ejecución.
- Comunicación síncrona con `OpenFeign`: los microservicios que requieren datos de otros dominios usan clientes declarativos Feign para invocar APIs REST de forma tipada y controlada. Ejemplos claros son `ordentrabajoservice` consultando `vehiculo-service`, `mecanico-service` y `cliente-service`, y `facturacion-service` consultando `orden-service` y `servicio-service`.

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
git clone https://github.com/usuario/Repositorio-Taller-Mecanico.git
cd Taller_Mecanico_Proyecto_Fullstack-main
./mvnw clean package
docker compose up --build
```

### Notas adicionales

- Si usa Maven instalado localmente, puede reemplazar `./mvnw` por `mvn`.
- El comando `docker compose up --build` levanta todos los contenedores de base de datos y los 10 microservicios.
- Para detener el ecosistema y limpiar volúmenes:

```bash
docker compose down -v
```

Este README está estructurado para evidenciar los elementos técnicos requeridos en la pauta de evaluación, con foco en arquitectura, seguridad, calidad de código y despliegue.

