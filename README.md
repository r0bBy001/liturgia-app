# 📖 Sistema de Gestión y Consulta de Horarios Litúrgicos

Este proyecto forma parte de una tesis universitaria. Permite registrar, consultar y gestionar los actos litúrgicos realizados en una o más iglesias.

## ⚙️ Tecnologías utilizadas

- **Backend:** Spring Boot 3.4.4 con Java 17
- **Base de datos:** PostgreSQL
- **Contenedor:** Podman
- **Entorno:** WSL Ubuntu
- **Documentación:** Swagger (OpenAPI 2.2.0)
- **Subida de imágenes:** (en proceso de implementación para conversión a WebP)

## 🧩 Funcionalidades principales

- CRUD de Iglesias
- CRUD de Actos Litúrgicos
- Gestión de Tipos de Actos
- Usuarios con roles:
  - `SUPERADMIN`: administra todo el sistema
  - `ENCARGADO`: gestiona solo su iglesia asignada
- Módulo de banners, galería, noticias y eventos (en desarrollo)

## 📁 Estructura del backend

El proyecto está organizado por módulos funcionales. Cada módulo contiene:

- `controller`
- `service`
- `repository`
- `dto`
- `model`

Ejemplo de estructura:

src/ └── main/ └── java/com/example/demo/ ├── Iglesia/ ├── ActoLiturgico/ ├── TipoActo/ ├── Usuario/ └── ...

## 🔧 Configuración de la base de datos (application.properties)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/liturgia_db
spring.datasource.username=admin
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=update
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```
📄 Documentación Swagger
Disponible en:
http://localhost:8080/swagger-ui/index.html
