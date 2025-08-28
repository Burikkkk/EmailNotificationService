# === Этап сборки ===
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем pom и src
COPY pom.xml .
COPY src ./src

# Собираем jar без тестов
RUN mvn clean package -DskipTests

# === Этап запуска ===
FROM eclipse-temurin:17-jre
WORKDIR /app

# Копируем собранный jar из этапа build
COPY --from=build /app/target/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]

