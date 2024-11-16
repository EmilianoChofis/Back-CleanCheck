FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests
#aqui termina etapa de build
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/CleanCheck-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
#en la sig linea cada que demos run a este contenedor esto se ejecuta; es decir se levanta la aplicación; asi se escrib
ENTRYPOINT ["java","-jar","app.jar"]
