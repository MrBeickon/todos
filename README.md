# 📝 API de Gestión de Tareas

API RESTful para la gestión de tareas personales (to-do), desarrollada como parte de un desafío técnico utilizando Java
21, Spring Boot 3.5 y MongoDB. El entorno completo se ejecuta en contenedores Docker para facilitar la portabilidad y la
ejecución local.

## 🎯 Objetivo

Permitir la creación, consulta, filtrado, actualización, completado y eliminación de tareas, con atributos como título,
descripción, prioridad, fecha de vencimiento y estado.

## 🚀 Funcionalidades

- 🔑 Generar token de acceso
- 📌 Crear una tarea
- 📋 Listar tareas con filtros
- 🔍 Consultar una tarea por ID
- 📝 Actualizar una tarea
- ✅ Marcar una tarea como en curso
- ✅ Marcar una tarea como completada
- ❌ Eliminar una tarea

## ⚙️ Tecnologías utilizadas

- Java 21
- Spring Boot 3.5
- MongoDB
- Docker y Docker Compose
- Maven
- OpenAPI 3.0 (Swagger UI)

## 🐳 Docker y MongoDB

Para levantar el proyecto con Docker, necesitas tener instalado Docker Desktop en tu máquina.

- Puedes descargarlo aquí: [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

La aplicación y la base de datos están contenidas en servicios separados y definidos en `docker-compose.yml`.

- El servicio `app` construye el contenedor a partir del `Dockerfile`, expone el puerto 8080 y se comunica con MongoDB.
- El servicio `mongo` usa la imagen oficial de MongoDB y persiste los datos en un volumen local.

La URI de conexión se define vía variable de entorno:

```properties
spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/taskdb}
```

## 🧩 Enfoque API First

Este proyecto fue desarrollado siguiendo el enfoque **API First**.  
Se diseñó primero el contrato OpenAPI (versión 3.0.3), definiendo todos los endpoints, modelos y parámetros antes de escribir el código.

Ventajas:
- Permite discutir y validar la API con anticipación.
- Mejora la documentación y la experiencia de uso (Swagger UI).
- Facilita la generación de código cliente o servidor a futuro si se desea.

El archivo OpenAPI (`openapi.yaml`) se encuentra en la raíz del proyecto o en la carpeta `/openapi`.

## 📥 Importar la API en Postman

Puedes importar todos los endpoints en Postman fácilmente desde la especificación OpenAPI.

### 🔹 Opción 1: Desde Swagger UI

1. Asegúrate de que la API esté corriendo (`docker compose up`).
2. Abre Swagger UI en tu navegador:  
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
3. Haz clic en /v3/api-docs.
4. Copia el contenido.
5. Abre Postman.
6. Haz clic en **"Import"** (parte superior izquierda) y pega el contenido
7. Postman generará automáticamente una colección con todos los endpoints y ejemplos de requests.

### 🔹 Opción 2: Desde el archivo `openapi.yaml` local

1. Abre Postman.
2. Haz clic en **"Import"** (parte superior izquierda) y pega o arrastra el archivo `openapi.yaml`.
3. Postman generará automáticamente una colección con todos los endpoints y ejemplos de requests.

> Esto te permitirá probar la API fácilmente sin tener que configurar manualmente cada petición.


## ⚙️ Configuración y ejecución del proyecto

### 1. Clonar el repositorio

```bash
    git clone https://github.com/tu-usuario/api-gestion-tareas.git
    cd api-gestion-tareas/backend
```

### 2. Compilar la aplicación

```bash
    ./mvnw clean package -DskipTests
```

Este comando generará el archivo .jar dentro de target/.

### 3. Levantar los servicios con Docker

```bash
   docker-compose up --build
```

Esto levantará:
- MongoDB en localhost:27017
- La API en http://localhost:8080

## 📂 Documentación de la API

Una vez desplegado el entorno, accede a la documentación Swagger/OpenAPI en:

🔗 http://localhost:8080/swagger-ui.html


## 🌐 Endpoints disponibles (`/api/v1`)

| Método | Endpoint                     | Descripción                          |
|--------|------------------------------|--------------------------------------|
| `POST` | `/auth/token`                | Genera un token de acceso            |
| `POST` | `/api/v1/tasks`              | Crear una nueva tarea                |
| `GET`  | `/api/v1/tasks`              | Listar tareas con filtros opcionales |
| `GET`  | `/api/v1/tasks/{id}`         | Consultar una tarea por ID           |
| `PUT`  | `/api/v1/tasks/{id}`         | Actualizar una tarea                 |
| `PATCH`| `/api/v1/tasks/{id}/start`   | Marcar una tarea como en curso       |
| `PATCH`| `/api/v1/tasks/{id}/complete` | Marcar una tarea como completada     |
| `DELETE`| `/api/v1/tasks/{id}`         | Eliminar una tarea                   |