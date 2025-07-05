# Rideonix — Carsharing Web Application

**Rideonix** is a web-based carsharing application that allows users to register, browse available cars, and book vehicles for specific dates. 
The system also includes an admin interface for managing cars and bookings.

---

## 🚗 Features

- 🔐 User registration and authentication (JWT-based)
- 🛻 Car catalog with filters and search
- 📅 Booking system with real-time availability
- ❌ Booking cancellation with rules (e.g., min 1 day before)
- 🧑‍💼 Admin panel for managing cars and reservations
- 🌐 Integration with Telegram Web App (in progress)

---

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot** (MVC, Security, Data JPA)
- **Thymeleaf** for dynamic HTML rendering
- **PostgreSQL** as database
- **Bootstrap** for UI
- **JWT** for authentication and role management

---

## ⚙️ Configuration

Create a file named `src/main/resources/application.yml` by copying `application-example.yml`, and fill in the actual database access credentials.  
> ⚠️ This file should **not** be committed to the repository!

```yaml
server:
  port: ${PORT:8080}

spring:
  datasource:
    url: jdbc:postgresql://<host>:5432/<dbname>
    username: <username>
    password: <password>
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

  thymeleaf:
    cache: false
```

🧑‍💻 Author
Developed by Egor Epur
GitHub: Bugs-Bunny96

## ⚠️ Notice

This project is published for demonstration and educational purposes only.  
Reproduction or usage of its content without permission is not allowed.

