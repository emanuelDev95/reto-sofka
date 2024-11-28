# Usa una imagen base de OpenJDK 21
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido en el contenedor
COPY target/reto-persona-0.0.1-SNAPSHOT.jar  /app/reto-persona-0.0.1-SNAPSHOT.jar

# Expone el puerto donde correrá el servicio
EXPOSE 8081

# Comando para ejecutar la aplicación de Spring Boot
ENTRYPOINT ["java", "-jar", "/app/reto-persona-0.0.1-SNAPSHOT.jar"]
