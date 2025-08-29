# Email Notification System

Два микросервиса:
- **auth-service** — аутентификация/авторизация, CRUD пользователей (MySQL, Flyway, JWT, Spring Security, Lombok, Swagger).
- **notification-service** — слушает события (RabbitMQ) и отправляет email‑уведомления администраторам через MailHog (тестовый SMTP).

## Запуск
Перед запуском необходимо поместить файл .env в корень проекта.
```bash
# Запуск с помощью Docker
docker compose up --build
```
## Тестовые пользователи

| Роль  | Имя пользователя | Пароль     | Email             |
|-------|-----------------|-----------|-----------------|
| ADMIN | admin           | admin123  | admin@example.com |
| ADMIN | ivan            | 123       | ivan@example.com  |
| ADMIN | olga            | 123       | olga@example.com  |
| USER  | user            | user123   | user@example.com  |
| USER  | alex            | 123       | alex@example.com  |
| USER  | maria           | 123       | maria@example.com |

## Пример регистрации
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newUser",
    "password": "111",
    "email": "newuser@example.com",
    "firstName": "new",
    "lastName": "User",
    "role": "USER"
  }'
```
## Пример авторизации
```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "admin", "password": "admin123"}'
```

## CRUD примеры
```bash
TOKEN_USER=$(curl -s http://localhost:8080/api/auth/login -H 'Content-Type: application/json'   -d '{"username":"user","password":"user123"}' | jq -r .token)
TOKEN_ADMIN=$(curl -s http://localhost:8080/api/auth/login -H 'Content-Type: application/json'   -d '{"username":"admin","password":"admin123"}' | jq -r .token)

# USER меняет свои данные
curl -X PUT http://localhost:8080/api/profile \
-H "Authorization: $USER_TOKEN" \
-H "Content-Type: application/json" \
-d '{
    "username": "newData",
    "password": "new123",
    "email": "newData@example.com",
    "firstName": "new",
    "lastName": "123",
    "role": "USER"
  }'

# ADMIN удаляет пользователя по id
curl -X DELETE http://localhost:8080/api/users/id \
-H "Authorization: $TOKEN_ADMIN"

# ADMIN получает список всех
curl -X GET http://localhost:8080/api/users \
-H "Authorization: $TOKEN_ADMIN"
```
