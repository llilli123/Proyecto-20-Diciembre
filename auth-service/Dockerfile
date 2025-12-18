# =========================
# BUILD STAGE
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Código fuente
COPY src ./src

# Build del jar
RUN mvn -q -DskipTests package


# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Puerto interno: en tu config usa server.port=0 (random)
# En Docker/Compose normalmente lo publicas via gateway, pero para pruebas puedes mapear a un puerto fijo.
EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/app.jar"]