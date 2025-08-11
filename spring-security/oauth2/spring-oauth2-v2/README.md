# 🚀 Aplicación Web de Gestión de Usuarios con OAuth2 y JWT

## 📝 Descripción General

Esta aplicación web está compuesta por varios componentes integrados para gestionar la autenticación, registro y edición de usuarios usando tecnologías modernas y seguras.  
La solución combina un backend en Spring Boot con Java 17, un frontend sencillo en React con Vite, y servicios auxiliares para caching y persistencia.

---

## 🧩 Componentes

### 🖥️ Backend - Spring Boot (Java 17)

- Implementa toda la lógica de la aplicación.  
- Autenticación y autorización con OAuth2, soportando:  
  - 🔑 Google OAuth2  
  - 🔐 GitHub OAuth2  
- Verificación de email mediante el uso de app password de Google para envío de correos.  
- Registro y login de usuarios con credenciales propias o mediante OAuth2.  
- Manejo de tokens JWT para acceso seguro.  
- Lógica de refresh token con almacenamiento y cacheo en Redis para mejorar rendimiento y seguridad.
- Para instalar las dependencias y compilar el proyecto, se debe usar Maven.
- Es necesario configurar las variables de entorno requeridas según el archivo application.properties, incluyendo las credenciales OAuth, conexión a Redis y MySQL, y claves JWT.

---

### 🎨 Frontend - React con Vite (Node 18)

- Interfaz simple usada como plantilla visual.  
- Funcionalidades:  
  - 📝 Registro de usuarios.  
  - 🔒 Inicio de sesión mediante OAuth2 o con usuario registrado.  
  - 🖼️ Edición de perfil de usuario (avatar, email, etc.).  
  - 🔑 Edición de contraseña.  
- Foco en demostrar gestión de estados y flujos de autenticación.

#### ⚙️ Cómo levantar el frontend en el contenedor

Para levantar el frontend dentro del contenedor Docker, se debe:

1. Entrar al contenedor del frontend:  
```bash
docker exec -it <nombre_contenedor_frontend> /bin/bash
```
Instalar las dependencias con npm:

```bash
npm install
```

Levantar el servidor de desarrollo Vite, exponiendo el host para que sea accesible:

```bash
npm run dev -- --host
```

Esto permitirá que el frontend esté corriendo dentro del contenedor y sea accesible desde el navegador en la máquina host.

###  🗄️ Redis
Utilizado para la gestión y lógica de tokens:

Cacheo de refresh tokens.

Almacenamiento temporal de tokens para verificación de email.

### 🛢️ MySQL
Base de datos relacional para persistencia de usuarios y sus datos.

Almacena información registrada en el backend.

## 🧪 Pruebas y Ejecución
Se provee un archivo docker-compose.yml para levantar todos los servicios necesarios (Redis, MySQL, frontend).

❗ El backend no está incluido en el docker-compose por problemas de build en el entorno actual.

Para probar la aplicación, se recomienda levantar el backend localmente y luego iniciar el resto de los servicios mediante Docker Compose.

## 📋 Requisitos Previos
☕ Java 17 (para backend local).

⚛️ Node 18 (para frontend local o dentro del contenedor).

🐳 Docker y Docker Compose (para servicios Redis y MySQL, y frontend si se usa contenedor).

## 🚀 Instrucciones Básicas
Clonar el repositorio.

Levantar el fronted, Redis y MySQL con:

```bash
docker-compose up -d
```

Levantar el backend localmente (por ejemplo, con Maven o tu IDE preferido).

Iniciar el frontend dentro del contenedor (ver sección anterior) con:

```bash
npm install
npm run dev -- --host
```

Acceder a la app en el navegador para probar flujos de registro, login, edición, etc.

## ⚠️ Notas
Debido a limitaciones del entorno Docker actual, el backend no está dockerizado en el compose.

La aplicación está diseñada para poder extenderse fácilmente con nuevas funcionalidades y proveedores OAuth2 adicionales.