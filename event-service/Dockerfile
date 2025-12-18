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

# Empaquetar jar
RUN mvn -q -DskipTests package


# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# En tu config usas server.port=0 (random). EXPOSE es informativo.
EXPOSE 8082

ENTRYPOINT ["java","-jar","/app/app.jar"]