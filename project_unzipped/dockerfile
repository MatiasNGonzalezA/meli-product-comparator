# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package

# ---- Run stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Usuario no root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
# Puerto de la app
EXPOSE 8080
# Copia el JAR generado
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar
# JVM sensata en contenedores
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 -XX:+ExitOnOutOfMemoryError"
# Perfil por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java","-jar","/app/app.jar"]