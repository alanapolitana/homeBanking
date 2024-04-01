# Primera etapa: construir la aplicación
FROM gradle:8.2.1-jdk11 AS build
COPY build.gradle settings.gradle /home/gradle/project/
WORKDIR /home/gradle/project
RUN gradle build || return 0  # Solo para descargar dependencias y compilar, no importa si falla
COPY . .
RUN gradle build

# Segunda etapa: empaquetar la aplicación en una imagen de Java
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar /app/homebanking.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "homebanking.jar"]
