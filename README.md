# ERP System

## Overview
The ERP System is a backend service managing **Products, Orders, Users, and Inventory**.  
It provides full CRUD APIs and serves as the central system for managing products, orders, users, and inventory.

## Features
- CRUD operations for Products, Orders, and Users
- Inventory management
- Authentication & Authorization (JWT, OAuth-ready, RBAC)
- Validation for requests
- RESTful APIs with JSON responses
- Database persistence (PostgreSQL / MySQL / H2)
- Exception handling with centralized global error handler
- Multi-tenant ready (future implementation)

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- Lombok
- Maven

## Setup & Run
1. Configure your `application.properties` (or environment variables) with database and other settings.
2. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Modules & Folders
- **authentication** – login, registration, refresh, JWT/OAuth token services
- **user** – user entity, DTOs, mappers, service, RBAC roles
- **order** – orders and order items, DTOs, mappers, service
- **inventory** – inventory management, adjustments, DTOs, mappers, service
- **publication** – product/publication management
- **integration/bridge** – external integration clients, DTOs, and mappers
- **exceptions** – centralized error handling, custom exception classes
- **config** – global application configuration, initial data setup
- **docs** – architecture and workflow documentation (authflow, orderflow, inventoryflow)

## Endpoints

| Resource | Endpoint | Method |
|----------|----------|--------|
| Users | /api/users | GET |
| User | /api/users/{id} | GET |
| User | /api/users | POST |
| User | /api/users/{id} | PUT |
| User | /api/users/{id} | PATCH |
| User | /api/users/{id} | DELETE |
| Orders | /api/orders | GET |
| Order | /api/orders/{id} | GET |
| Order | /api/orders | POST |
| Order | /api/orders/{id} | PUT |
| Order | /api/orders/{id} | PATCH |
| Order | /api/orders/{id} | DELETE |
| Products | /api/publications | GET |
| Product | /api/publications/{id} | GET |
| Product | /api/publications | POST |
| Product | /api/publications/{id} | PUT |
| Product | /api/publications/{id} | PATCH |
| Product | /api/publications/{id} | DELETE |
| Inventory | /api/inventory | GET |
| Inventory | /api/inventory/{id} | GET |
| Inventory Adjustment | /api/inventory/adjustments | POST |

## Authentication Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| /api/auth/login | POST | Login user and return JWT |
| /api/auth/register | POST | Register user and return JWT |
| /api/auth/refresh | POST | Refresh JWT using refresh token |

## Notes
- All endpoints are secured by roles (RBAC), e.g., `ADMIN`, `EDITOR`, `VIEWER`, `AUTHOR`.
- Input validation is enforced on all request DTOs.
- Global exception handling is implemented; all errors return structured JSON responses.
- Ready for OAuth integration in the future.

