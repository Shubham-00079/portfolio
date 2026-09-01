FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=0 /app/target/*.jar app.jar

CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
