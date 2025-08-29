# === Этап сборки ===
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости отдельно (для кэширования)
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники
COPY src ./src

# Собираем jar без тестов
RUN mvn clean package -DskipTests

# === Этап запуска ===
FROM eclipse-temurin:17-jre
WORKDIR /app

# Копируем собранный jar из этапа build
COPY --from=build /app/target/*.jar app.jar

# Открываем порт 8080 (тот же, что в docker-compose)
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
