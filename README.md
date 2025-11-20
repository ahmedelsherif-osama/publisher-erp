# ERP System

## Overview
The ERP System is a backend service managing **Products, Suppliers, and Orders**. It provides full CRUD APIs and serves as the central system for inventory, suppliers, and order management.

## Features
- CRUD operations for Products, Suppliers, and Orders
- RESTful APIs with JSON responses
- Database persistence (PostgreSQL / MySQL / H2)
- Exception handling with meaningful error messages

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- Lombok
- Maven

## Setup & Run
1. Configure `application.properties` with your database settings
2. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Endpoints
| Resource | Endpoint | Method |
|----------|----------|--------|
| Products | /api/products | GET |
| Product | /api/products/{id} | GET |
| Product | /api/products | POST |
| Product | /api/products/{id} | PUT |
| Product | /api/products/{id} | PATCH |
| Product | /api/products/{id} | DELETE |
| Suppliers | /api/suppliers | GET |
| Supplier | /api/suppliers/{id} | GET |
| Orders | /api/orders | GET |
| Order | /api/orders/{id} | GET |
| Order | /api/orders | POST |
| Order | /api/orders/{id} | DELETE |

---
