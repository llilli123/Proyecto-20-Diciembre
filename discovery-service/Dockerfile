# =========================
# BUILD STAGE
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom primero para cachear dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Empaquetamos el JAR
RUN mvn -q -DskipTests package

# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Puerto Eureka
EXPOSE 8761

# Arranque
ENTRYPOINT ["java","-jar","/app/app.jar"]