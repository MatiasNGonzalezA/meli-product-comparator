# 🤖 PROMPTS.md - Uso de GenAI (ChatGPT)

Este documento detalla cómo se utilizó **GenAI (ChatGPT)** como apoyo en el desarrollo del challenge.

---

## 🏗️ Diseño de arquitectura
- Prompt: *“Ayudame a aplicar arquitectura hexagonal en este challenge, separando api, application, domain e infrastructure.”*
- Resultado: estructura de paquetes siguiendo el modelo **Hexagonal (Ports & Adapters)**.

---

## 🧩 Patrones de diseño
- Prompt: *“Explicame cómo aplicar Strategy para comparar productos por precio o rating.”*
- Prompt: *“Mostrame un ejemplo de cómo implementar un Decorator para cachear el repositorio.”*
- Resultado: implementación de **Strategy** (PRICE / RATING) y **Decorator** (cache).

---

## ⚙️ Configuración & tests
- Prompt: *“Cómo configurar application.yml con CORS y Swagger.”*
- Prompt: *“Corregime este test para que use Mockito correctamente.”*
- Resultado: configuración de `application.yml`, Swagger y tests unitarios con **Mockito**.

---

## 📖 Documentación
- Prompt: *“Armame un README.md con arquitectura, endpoints y ejemplos de ejecución.”*
- Prompt: *“Generame un PLAN.md y un RUN.md para este challenge.”*
- Resultado: documentación clara, con **README**, **PLAN** y **RUN**, más prompts documentados.

---

## 🎯 Estrategia técnica
- ChatGPT se utilizó como **asistente de desarrollo**, principalmente para:
    - Validar diseño de arquitectura.
    - Proponer ejemplos de código.
    - Mejorar cobertura de tests.
    - Estandarizar documentación.

---