# Paso 1: Usar una imagen oficial de Maven con Java 21 para compilar el código
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el archivo de configuración de Maven y el código fuente
COPY pom.xml .
COPY src ./src

# Compilar el proyecto omitiendo los tests para acelerar el proceso
RUN mvn clean package -DskipTests

# Paso 2: Usar una imagen liviana de Java 21 para ejecutar la aplicación empaquetada
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/vehiculoservice-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del microservicio
EXPOSE 8080

# Comando para arrancar el servicio ejecutable
ENTRYPOINT ["java", "-jar", "app.jar"]