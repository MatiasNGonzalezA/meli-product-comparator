# ▶️ Ejecución del Proyecto - Meli Product Comparator API

## 1. Clonar el repositorio

git clone https://github.com/MatiasNGonzalezA/meli-product-comparator.git
cd meli-product-comparator

## 2. Ejecutar con Maven

./mvnw spring-boot:run

La API estará disponible en:
👉 http://localhost:8080/api/products


## 3. Ejecutar tests
./mvnw test

## 4. Ejecutar con Docker (opcional)
docker build -t product-comparator .
docker run -p 8080:8080 product-comparator

La API quedará disponible en:
👉 http://localhost:8080/api/products


## 5. Documentación Swagger

Cuando la aplicación esté levantada, acceder a:
👉 http://localhost:8080/swagger-ui

