# Backend VillaMarkets

API REST construida con **Spring Boot 3** y **MySQL** que sustenta el proyecto VillaMarkets. Expone servicios para autenticacion, gestion de usuarios, productos, carritos y pedidos, y se despliega en una instancia AWS EC2.

## Tabla de contenidos
1. [Arquitectura](#arquitectura)
2. [Prerequisitos](#prerequisitos)
3. [Configuracion](#configuracion)
4. [Ejecucion local](#ejecucion-local)
5. [Scripts Maven utiles](#scripts-maven-utiles)
6. [Estructura del proyecto](#estructura-del-proyecto)
7. [Seguridad y JWT](#seguridad-y-jwt)
8. [Documentacion de APIs](#documentacion-de-apis)
9. [Despliegue en AWS](#despliegue-en-aws)
10. [Pruebas](#pruebas)

## Arquitectura
- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.3 (Web, Data JPA, Validation, Security)
- **Base de datos**: MySQL 8 (local en EC2)
- **Build**: Maven Wrapper (`mvnw`)
- **Otros**: Lombok, SpringDoc OpenAPI, BCrypt

## Prerequisitos
- JDK 21 instalado
- Maven 3.9+ (opcional si usas `mvnw`)
- MySQL con un schema creado (ver `application.properties`)

## Configuracion
Edita `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/villamarkets_db_ec2
spring.datasource.username=villa
spring.datasource.password=Villa_Markets2025!
spring.jpa.hibernate.ddl-auto=update
```
Ajusta credenciales/host segun tu entorno. Para entornos locales puedes usar variables de entorno o un archivo `application-local.properties`.

## Ejecucion local
```bash
./mvnw spring-boot:run
```
La API quedara disponible en `http://localhost:8080/api/v1`.

## Scripts Maven utiles
| Comando | Descripcion |
| ------- | ----------- |
| `./mvnw clean package` | Compila y genera el JAR ejecutable. |
| `./mvnw test` | Ejecuta pruebas unitarias. |
| `./mvnw spring-boot:run` | Inicia el servidor embebido. |

## Estructura del proyecto
```
src/main/java/com/example/backend
  config/          # SecurityConfig y configuraciones generales
  controller/      # REST controllers (UsuarioController, CarritoController, etc.)
  dto/             # Data Transfer Objects
  model/           # Entidades JPA
  repository/      # Interfaces Spring Data JPA
  security/        # Filtros y utilidades JWT (si aplica)
  service/         # Logica de negocio
src/main/resources
  application.properties
```

## Seguridad y JWT
- `UsuarioController` maneja `/api/v1/usuarios/login` y `/register`.
- El token (JWT) se genera en el login y se envia al frontend junto con los datos del usuario.
- `SecurityConfig` habilita CORS, desactiva CSRF y protege rutas segun rol (`ROLE_ADMIN`, `ROLE_CLIENTE`).
- Si aun no estas usando JWT, puedes permitir todas las rutas `/api/v1/**` mientras habilitas la integracion.

## Documentacion de APIs
SpringDoc expone la documentacion en `http://localhost:8080/swagger-ui/index.html`.
Endpoints principales:
- `POST /api/v1/usuarios/login`
- `POST /api/v1/usuarios/register`
- `GET/POST/PUT/DELETE /api/v1/productos`
- `GET/POST /api/v1/carritos/usuario/{usuarioId}`
- `POST /api/v1/pedidos`
Cada endpoint esta anotado con `@Operation` y describe request/response.

## Despliegue en AWS
1. Empaqueta el proyecto: `./mvnw clean package`.
2. Sube el JAR a la instancia EC2 (Ubuntu) y crea un servicio `systemd` que ejecute `java -jar backend-villamarkets2.jar`.
3. Configura MySQL en la misma instancia o en RDS.
4. Abre el puerto 8080 en el Security Group.
5. Para produccion considera colocar un proxy Nginx con HTTPS y apuntar el frontend (S3/CloudFront) a esa URL.

## Pruebas
- Los servicios cuentan con pruebas unitarias basadas en JUnit 5 y Mockito (ubicadas en `src/test/java`).
- Para pruebas manuales usa Postman/Thunder Client apuntando a `http://localhost:8080/api/v1`.

Proyecto mantenido para el curso **DSY1104 - Desarrollo Fullstack II**.
