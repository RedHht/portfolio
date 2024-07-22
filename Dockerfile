# Usar una imagen base de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usar una imagen base de Java para ejecutar la aplicación
FROM openjdk:17-jre-slim
COPY --from=build target/redhht-0.0.1-SNAPSHOT.jar /app/portfolio.jar
ENTRYPOINT ["java", "-jar", "portfolio.jar"]
