# 🛒 Meli Product Comparator API

API REST para listar, obtener detalle y comparar productos.  
Challenge técnico.

---

## 🚀 Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3**
- **Maven**
- **JUnit 5 + Mockito**
- **Springdoc OpenAPI (Swagger UI)**
- **Arquitectura Hexagonal**
- **Patrones de diseño:** Strategy, Decorator, Adapter
- **Docker (opcional)**

---

## 📂 Arquitectura
El proyecto está diseñado siguiendo el estilo **Hexagonal (Ports & Adapters)**:

- **api** → Exposición de la API (controllers, DTOs, mappers).
- **application** → Lógica de aplicación (services, puertos de entrada/salida).
- **domain** → Núcleo de negocio (modelos, estrategias, reglas de dominio).
- **infrastructure** → Implementaciones técnicas (repositorios, decoradores, configuraciones).
- **exception** → Manejo centralizado de errores.

Esto permite aislar el **core de negocio** de las dependencias externas.

---

## 🧩 Patrones aplicados
- **Strategy:** comparación de productos (precio o rating).
- **Decorator:** capa de caching sobre el repositorio.
- **Adapter:** conexión a distintas fuentes de datos (archivo JSON local, API externa).

---
s
## 📦 Endpoints principales
Base path: `/api`

- `GET /products`  
  Lista todos los productos.

- `GET /products/{id}`  
  Obtiene detalle de un producto.

- `GET /products/compare?ids=A,B`  
  Compara una lista de productos.
  - Estrategia default: **PRICE**
  - Param opcional: `strategy=RATING`

Ejemplo:

     "http://localhost:8080/api/products/compare?ids=1,2&strategy=RATING"

## 🏗️ Ejecución local

👉 Ver el archivo run.md para instrucciones de ejecución local, tests y docker.




## 🧪 Tests

    Ejecutar todos los tests con: 
      ./mvnw test

incluyen pruebas unitarias de servicios, repositorios y controladores (con Mockito).


## 🐳 Ejecución con Docker (opcional)

    Construir imagen y correr el contenedor:
    
        docker build -t product-comparator .
        docker run -p 8080:8080 product-comparator



## 📖 Documentación Swagger

    Cuando la app esté levantada:
    👉 http://localhost:8080/swagger-ui



## 📝 Configuración

    El archivo application.yml define:
        •	Puerto (8080)
        •	Context-path (/api)
        •	CORS habilitado para http://localhost:5173 y http://localhost:3000
        •	Fuente de datos por defecto: classpath:data/products.json
        •	Opciones de paginación, filtrado y ordenamiento


## 📊 Diagrama simple
  ```text
+------------------+
| ProductController|
+------------------+
         |
         v
+------------------+
|  ProductService  |
+------------------+
     |          |
     v          v
+------------------+    +------------------+
|  FileProductRepo |    | CachedRepository |
+------------------+    +------------------+
            \              /
             v            v
           +------------------+
           |   Product (dom)  |
           +------------------+


## 👤 Autor

    Matías González
    Desarrollador Java Backend



