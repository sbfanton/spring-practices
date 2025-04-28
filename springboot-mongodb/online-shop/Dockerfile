# Etapa 1: Build del jar
FROM maven:3.6.3-openjdk-8 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (mejora cacheo)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final, solo con Java y el jar
FROM openjdk:8-jdk-alpine

RUN apk add --no-cache bash zsh

WORKDIR /app

# Copiar solo el jar compilado
COPY --from=build /app/target/*.jar online-shop.jar

# Exponer el puerto de Spring Boot
EXPOSE 9091

# Comando de arranque
ENTRYPOINT ["java", "-jar", "online-shop.jar"]