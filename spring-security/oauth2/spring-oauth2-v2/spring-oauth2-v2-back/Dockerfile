# Etapa 1: Build con Maven y Java 17
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml /app/

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java 17
FROM openjdk:17-jdk-alpine

RUN apk add --no-cache bash zsh

COPY --from=build /app/target/*.jar oauthclient.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "oauthclient.jar"]