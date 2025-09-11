# 📌 Plan del Proyecto - Meli Product Comparator API

## 🎯 Objetivo
Desarrollar una API REST que permita listar productos, obtener su detalle y compararlos mediante distintas estrategias (precio o rating).  
El objetivo principal es demostrar buenas prácticas de arquitectura, manejo de errores y aplicación de patrones de diseño.

---

## 📦 Alcance
- **Incluye:**
    - Listar productos desde una fuente de datos local (archivo JSON).
    - Obtener detalle de un producto por ID.
    - Comparar múltiples productos utilizando estrategias configurables (PRICE, RATING).
    - Documentación automática con Swagger.
    - Tests unitarios con JUnit y Mockito.
- **No incluye:**
    - Persistencia en base de datos real.
    - Integraciones externas productivas (solo adapter opcional).

---

## ⚙️ Estrategia técnica
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3
- **Build:** Maven
- **Testing:** JUnit 5 + Mockito
- **Documentación:** Springdoc OpenAPI
- **Arquitectura:** Hexagonal (Ports & Adapters)
- **Patrones aplicados:** Strategy, Decorator, Adapter
- **Infraestructura:** Docker (opcional)

Se utilizaron **herramientas modernas de desarrollo** como IntelliJ IDEA, Maven Wrapper y **GenAI (ChatGPT)** para asistencia en documentación, validación de diseño y generación de ejemplos de código, lo que permitió acelerar la entrega y mejorar la claridad de la solución.

---
## 🧭 Visión general estratégica
- **La estrategia de diseño se basó en los siguientes pilares:**
1.	Separación de responsabilidades (Arquitectura Hexagonal):
•	El dominio permanece aislado de frameworks o detalles de infraestructura.
•	La lógica de negocio no depende de la fuente de datos (archivo, API externa, cache).
2.	Extensibilidad y mantenimiento:
•	Uso de patrones (Strategy, Decorator, Adapter) para incorporar nuevas fuentes de datos o estrategias de comparación con bajo impacto.
3.	Eficiencia y escalabilidad:
•	Decorator de cache para evitar lecturas repetitivas.
•	Compresión HTTP y configuración de CORS para optimizar la comunicación con frontends.
4.	Documentación y autodescubrimiento:
•	Swagger / OpenAPI para exploración interactiva de la API.
•	Diagramas y documentación técnica para facilitar onboarding de nuevos desarrolladores.

---

## 🕒 Estimación de tiempo
- Diseño inicial y setup: 2 horas
- Implementación de la API: 4 horas
- Tests unitarios y validaciones: 2 horas
- Documentación y empaquetado: 2 horas

Tiempo total estimado: **~10 horas**