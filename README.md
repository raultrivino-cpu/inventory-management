# Inventory Management

Inventory Management es una aplicación full-stack para la gestión de empresas, productos e inventario. El sistema permite autenticar usuarios con roles, administrar empresas y productos, consultar inventario por empresa, generar un reporte PDF y enviar dicho reporte por correo electrónico.

El proyecto fue desarrollado como solución para una prueba técnica utilizando Spring Boot en el backend y Vue.js con Quasar Framework en el frontend.

---

## Tabla de contenido

* [Tecnologías utilizadas](#tecnologías-utilizadas)
* [Funcionalidades principales](#funcionalidades-principales)
* [Roles del sistema](#roles-del-sistema)
* [Arquitectura del proyecto](#arquitectura-del-proyecto)
* [Modelo de datos](#modelo-de-datos)
* [Seguridad](#seguridad)
* [Configuración de base de datos](#configuración-de-base-de-datos)
* [Configuración de correo](#configuración-de-correo)
* [Ejecución del backend](#ejecución-del-backend)
* [Ejecución del frontend](#ejecución-del-frontend)
* [Endpoints principales](#endpoints-principales)
* [Credenciales de prueba](#credenciales-de-prueba)
* [Flujo funcional](#flujo-funcional)
* [Consideraciones técnicas](#consideraciones-técnicas)
* [Autor](#autor)

---

## Tecnologías utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* BCrypt
* OpenPDF
* Spring Mail
* Maven Wrapper

### Frontend

* Vue.js
* Quasar Framework
* Vite
* Pinia
* Axios
* Sass
* ESLint
* Prettier

### Herramientas de apoyo

* Postman
* Mailtrap
* Git
* GitHub
* Chatgpt

---

## Funcionalidades principales

La aplicación permite:

* Autenticación de usuarios contra base de datos.
* Manejo de roles `ROLE_ADMIN` y `ROLE_EXTERNAL`.
* Protección de endpoints según rol.
* Protección de rutas en frontend según rol.
* Gestión de empresas.
* Gestión de productos por empresa.
* Asociación de productos con categorías.
* Consulta de inventario por empresa.
* Generación de PDF del inventario.
* Envío del PDF del inventario por correo electrónico.
* Validación de formato de correo en frontend y backend.
* Manejo centralizado de excepciones.

---

## Roles del sistema

### Administrador

El usuario administrador por medio de la aplicación web puede:

* Iniciar sesión.
* Consultar empresas.
* Crear empresas.
* Editar empresas.
* Eliminar empresas.
* Consultar productos.
* Crear productos asociados a una empresa.
* Editar productos.
* Eliminar productos.
* Consultar inventario por empresa.
* Descargar el inventario en PDF.
* Enviar el PDF del inventario por correo electrónico.

A nivel de API, el rol administrador tiene acceso a las operaciones administrativas principales del sistema, incluyendo los endpoints CRUD de:

* Empresas
* Productos
* Categorías
* Clientes
* Órdenes de compra
* Roles
* Usuarios

Esto permite que el backend exponga una funcionalidad más completa que la interfaz web implementada para la prueba técnica. La aplicación web se enfocó en los flujos principales solicitados: empresas, productos, inventario, PDF y envío por correo.

### Externo

El usuario externo puede:

* Iniciar sesión.
* Consultar empresas.
* Visualizar información permitida como usuario visitante.

El usuario externo no puede:

* Crear empresas.
* Editar empresas.
* Eliminar empresas.
* Acceder al formulario de productos.
* Crear productos.
* Editar productos.
* Eliminar productos.
* Acceder a funcionalidades administrativas.

A nivel de API, el rol externo tiene acceso restringido. Principalmente puede consultar información permitida, como empresas, mientras que las operaciones de creación, edición, eliminación y administración quedan reservadas para el rol administrador.

---

## Arquitectura del proyecto

El proyecto está organizado como una aplicación full-stack en un mismo repositorio.

```text
inventory-management/
├── src/                  # Backend Spring Boot
├── frontend/             # Frontend Vue / Quasar
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

### Backend

El backend sigue una arquitectura por capas:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

También se utilizan DTOs y mappers para evitar exponer directamente toda la estructura interna de las entidades en algunos flujos.

### Frontend

El frontend se organiza principalmente en:

```text
src/
├── layouts/
├── pages/
├── router/
├── services/
└── stores/
```

* `pages`: contiene las pantallas principales.
* `layouts`: contiene la estructura visual principal.
* `router`: define rutas y guards de navegación.
* `stores`: maneja estado global con Pinia.
* `services`: centraliza llamadas HTTP con Axios.

---

## Modelo de datos

El sistema incluye las siguientes entidades principales:

* `Company`
* `Product`
* `Category`
* `Customer`
* `PurchaseOrder`
* `Role`
* `User`

Relaciones principales:

* Una empresa puede tener varios productos.
* Un producto pertenece a una empresa.
* Un producto puede tener varias categorías.
* Un cliente puede tener varias órdenes.
* Una orden puede estar relacionada con varios productos.
* Un usuario tiene un rol asociado.

---

## Seguridad

La seguridad se implementó con Spring Security usando Basic Auth.

El flujo de autenticación funciona así:

1. El frontend envía usuario y contraseña usando Basic Auth.
2. Spring Security valida las credenciales.
3. El `CustomUserDetailsService` consulta el usuario en base de datos.
4. La contraseña se valida usando BCrypt.
5. El rol del usuario se convierte en autoridad de Spring Security.
6. El backend permite o bloquea endpoints según el rol.

Roles usados:

```text
ROLE_ADMIN
ROLE_EXTERNAL
```

La aplicación también configura CORS para permitir que el frontend local en `http://localhost:9000` consuma el backend en `http://localhost:8080`.

---

## Configuración de base de datos

La aplicación utiliza PostgreSQL.

Crear la base de datos:

```sql
CREATE DATABASE inventory_db;
```

Configuración principal en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=false
```

> Nota: el proyecto usa `ddl-auto=validate`, por lo que las tablas deben existir previamente en la base de datos.

---

## Configuración de correo

Para el envío de correos se utiliza Spring Mail.

Durante el desarrollo local se usó Mailtrap como servidor SMTP de pruebas. Mailtrap permite validar el envío de correos y adjuntos sin enviar mensajes reales a usuarios finales.

Configuración recomendada:

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Las credenciales SMTP no deben subirse al repositorio.

### Variables de entorno en PowerShell

```powershell
$env:MAIL_USERNAME="your_mailtrap_username"
$env:MAIL_PASSWORD="your_mailtrap_password"
.\mvnw.cmd spring-boot:run
```

### Variables de entorno en CMD

```cmd
set MAIL_USERNAME=your_mailtrap_username
set MAIL_PASSWORD=your_mailtrap_password
mvnw.cmd spring-boot:run
```

---

## Ejecución del backend

Desde la raíz del proyecto:

### PowerShell

```powershell
.\mvnw.cmd spring-boot:run
```

### CMD

```cmd
mvnw.cmd spring-boot:run
```

El backend queda disponible en:

```text
http://localhost:8080
```

---

## Ejecución del frontend

Desde la carpeta `frontend`:

```cmd
cd frontend
npm install
npx quasar dev
```

El frontend queda disponible en:

```text
http://localhost:9000
```

---

## Endpoints principales

### Autenticación

```http
GET /api/auth/me
```

Retorna el usuario autenticado actual.

---

### Empresas

```http
GET    /api/empresas
GET    /api/empresas/{nit}
POST   /api/empresas
PUT    /api/empresas/{nit}
DELETE /api/empresas/{nit}
```

---

### Productos

```http
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}
```

---

### Categorías

```http
GET    /api/categorias
GET    /api/categorias/{id}
POST   /api/categorias
PUT    /api/categorias/{id}
DELETE /api/categorias/{id}
```

---

### Inventario

```http
GET  /api/inventario/empresa/{nitEmpresa}
GET  /api/inventario/empresa/{nitEmpresa}/pdf
POST /api/inventario/empresa/{nitEmpresa}/email
```

El inventario se consulta por empresa. El sistema trae los productos asociados a la empresa seleccionada.

El endpoint PDF genera un archivo con la información del inventario.

El endpoint email genera el mismo PDF y lo envía como adjunto al correo indicado.

---

## Credenciales de prueba

### Administrador

Este usuario esta configurado en ambiente local, en ambiente de producción se debe crear un usuario con el rol ADMIN

```text
Usuario: rtrivino
Contraseña: 12345
Rol: ROLE_ADMIN
```

### Externo

Este usuario esta configurado en ambiente local, en ambiente de producción se debe crear un usuario con el rol EXTERNAL

```text
Usuario: UsuarioExterno
Contraseña: 12345
Rol: ROLE_EXTERNAL
```

> Nota: reemplazar los valores de contraseña antes de entregar el proyecto.

---

## Flujo funcional

### Login

1. El usuario ingresa sus credenciales.
2. El frontend envía las credenciales al backend usando Basic Auth.
3. El backend valida usuario, contraseña y rol.
4. El frontend guarda la sesión y redirige al usuario.
5. Las rutas se habilitan o bloquean según el rol.

### Empresas

* El administrador puede crear, editar y eliminar empresas.
* El usuario externo solo puede consultar empresas.
* El frontend oculta botones administrativos cuando el usuario no tiene permisos.

### Productos

* El administrador puede registrar productos.
* Cada producto se asocia a una empresa.
* Cada producto puede tener una o varias categorías.
* Los productos incluyen precios en pesos, dólares y euros.

### Inventario

1. El usuario selecciona una empresa.
2. El sistema consulta los productos asociados a esa empresa.
3. Se muestra una tabla con los productos encontrados.
4. El usuario puede descargar la información en PDF.
5. El usuario puede enviar el PDF por correo electrónico.

### Operaciones administrativas por API

Además de las funcionalidades disponibles en la aplicación web, el backend expone endpoints REST para administrar las entidades principales del modelo de datos.

El rol `ROLE_ADMIN` puede consumir operaciones CRUD completas para:

* Empresas.
* Productos.
* Categorías.
* Clientes.
* Órdenes de compra.
* Roles.
* Usuarios.

Estas operaciones permiten validar que el backend cubre el modelo funcional completo, aunque la interfaz web se enfocó principalmente en los flujos solicitados para empresas, productos e inventario.

El rol `ROLE_EXTERNAL` tiene acceso restringido y no puede ejecutar operaciones administrativas como creación, edición o eliminación. Solo puede ejecutar el servicio GET (Consulta) de empresas

---

## Validaciones implementadas

* Validación de usuario y contraseña.
* Validación de permisos por rol en backend.
* Validación de rutas por rol en frontend.
* Validación de formularios en frontend.
* Validación de correo electrónico en frontend.
* Validación de correo electrónico en backend con `@Email` y `@NotBlank`.
* Validación defensiva del correo en el servicio de envío.
* Manejo de recursos no encontrados.
* Manejo de errores de validación.

---

## Manejo de errores

El backend cuenta con manejo centralizado de excepciones para responder errores de forma controlada.

Casos manejados:

* Recurso no encontrado.
* Argumentos inválidos.
* Errores de validación en request body.
* Errores internos en generación de PDF o envío de correo.

Esto evita exponer trazas internas al cliente y permite respuestas más claras desde la API.

---

## Consideraciones técnicas

* Se utilizó Basic Auth para mantener el alcance de autenticación simple y funcional.
* Las contraseñas se almacenan cifradas con BCrypt.
* El frontend usa Pinia para manejar el estado de autenticación.
* Axios centraliza las llamadas al backend.
* El inventario se modela funcionalmente como una consulta de productos por empresa.
* El PDF se genera en backend usando OpenPDF.
* El envío de correo se realiza con Spring Mail.
* Mailtrap se utiliza en ambiente local para pruebas de correo.
* Las credenciales SMTP deben configurarse mediante variables de entorno.
* El usuario externo tiene acceso limitado a funcionalidades de consulta, solo puede ver empresas.
* El usuario administrador tiene acceso a funcionalidades de gestión.

---

## Decisiones de diseño

### Inventario por empresa

El inventario se implementó como una vista de consulta de productos asociados a una empresa. Esto permite que el usuario seleccione una empresa y visualice los productos relacionados, junto con sus categorías y precios.

### Endpoint `/api/auth/me`

Se creó un endpoint específico para obtener el usuario autenticado actual. Este endpoint evita que el frontend tenga que consultar todos los usuarios para identificar quién inició sesión.

### PDF reutilizable

La generación del PDF se aisló en un servicio específico. Esto permite reutilizar el mismo PDF tanto para descarga directa como para envío por correo electrónico.

### Correo con Mailtrap

El envío de correo se probó usando Mailtrap para evitar correos reales en ambiente local. Esto facilita la validación del flujo de envío y adjuntos durante el desarrollo.

---

## Repositorio

```text
https://github.com/raultrivino-cpu/inventory-management
```

---

## Autor

Raúl Triviño
