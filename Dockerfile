# Etapa de construcción
FROM amazoncorretto:21-alpine-jdk
COPY postulaciones-management-system/target/postulaciones-management-system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
