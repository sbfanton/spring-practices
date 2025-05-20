# Proyecto de Implementación de OAuth2 en Spring Boot

Este proyecto es un ejemplo de implementación de OAuth2 utilizando Spring Boot. La estructura sigue el tutorial de *Ivana Soledad Rojas Corsico*, disponible en YouTube, que explica la creación de un sistema de autenticación utilizando tres módulos clave en OAuth2. 

### Tutorial de referencia:
[**Video Tutorial de Ivana Soledad Rojas Corsico**](https://www.youtube.com/watch?v=oHiIBkSv3nw)

## Descripción del Proyecto

El proyecto está compuesto por tres módulos principales, que funcionan juntos para demostrar el flujo completo de OAuth2. Estos módulos son:

1. **Servidor de Autorización (Authorization Server)**: Este servidor es propio, en lugar de utilizar servidores de autorización de terceros como Google, GitHub, etc. El servidor se encarga de autenticar al usuario y devolver un "authorization code" que posteriormente será intercambiado por un "access token".
   
2. **Servidor de Recursos (Resource Server)**: Este servidor proporciona los recursos protegidos (por ejemplo, datos sensibles) a los que se accede únicamente con un "access token" válido. Las peticiones al servidor de recursos requieren un token de acceso válido emitido por el servidor de autorización.

3. **Servidor Cliente (Client Server)**: Este servidor simula el cliente que solicita autenticación al servidor de autorización. El cliente solicita el código de autorización, luego intercambia ese código por el "access token", y finalmente realiza peticiones al servidor de recursos usando dicho token.

## Flujo de OAuth2

1. **El Cliente solicita el login**: El cliente envía una solicitud de autenticación al servidor de autorización.
   
2. **El servidor de autorización devuelve el código de autorización**: Si el usuario está autenticado correctamente, el servidor de autorización devuelve un "authorization code".

3. **El Cliente obtiene el Access Token**: El cliente utiliza el código de autorización recibido para obtener el "access token" en el servidor de autorización.

4. **El Cliente realiza peticiones al servidor de recursos**: Con el "access token", el cliente puede realizar solicitudes a recursos protegidos en el servidor de recursos.

## Características

- **Uso de OAuth2**: Implementación del flujo de autorización de OAuth2 utilizando Spring Boot.
- **Módulos independientes**: Los tres módulos (servidor de autorización, servidor de recursos y servidor cliente) están separados y se comunican entre sí.
- **Postman Collection**: Incluye una colección de Postman para realizar las pruebas necesarias y verificar el flujo de autenticación.
- **Pruebas con OAuth Debugger**: Se ha registrado como cliente para pruebas en el servidor de autorización OAuth Debugger para facilitar el proceso de prueba.

## Requisitos

- Java 17 o superior
- Maven
- Postman (para probar las peticiones)
- [OAuth Debugger](https://www.oauthdebugger.com/) para registrar el cliente y realizar pruebas.