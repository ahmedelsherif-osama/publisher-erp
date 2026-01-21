# ERP System

## Overview
The ERP System is a backend service managing **Publications, Orders, Users, and Inventory**.  
It serves as the central system of record for catalog management, order processing, inventory consistency, and external system integrations.

## Features
- CRUD operations for Publications, Orders, and Users
- Inventory management with adjustment tracking
- Authentication & Authorization (JWT, OAuth-ready, RBAC)
- Request validation and business rule enforcement
- RESTful APIs with JSON responses
- Database persistence (PostgreSQL / MySQL / H2)
- Centralized global exception handling
- Designed for external integrations (e-commerce bridge)
- Multi-tenant ready (future implementation)

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- Lombok
- Maven

## Setup & Run
1. Configure your `application.properties` (or environment variables).
2. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Modules & Folders
- **authentication** – login, registration, refresh, JWT/OAuth token services
- **user** – ERP users, roles (RBAC), profile management
- **publication** – publication and variant management (catalog)
- **order** – order creation, cancellation, completion
- **inventory** – inventory state and adjustment history
- **integration/bridge** – outbound integration with external systems
- **exceptions** – centralized error handling and custom exceptions
- **config** – application configuration and initial data setup
- **docs** – business and technical flow documentation

## Core Documentation
The following core system flows are documented in detail:
- **Authentication Flows** → `docs/authflow.md`
- **Order Flows** → `docs/orderflow.md`
- **Inventory Flows** → `docs/inventoryflow.md`
- **ERP–Ecommerce Integration** → `docs/erp-ecommerce-integration.md`

Supporting CRUD domains (users, publications) are intentionally not documented in detail.

## Endpoints (Summary)

### Users
| Endpoint | Method |
|--------|--------|
| /api/users | GET |
| /api/users/{id}/role | PATCH |

### Orders
| Endpoint | Method |
|--------|--------|
| /api/orders | GET |
| /api/orders | POST |
| /api/orders/{id}/cancel | POST |
| /api/orders/{id}/complete | POST |

### Publications
| Endpoint | Method |
|--------|--------|
| /api/publications | GET |
| /api/publications | POST |
| /api/publications/{id} | PUT |
| /api/publications/{id} | PATCH |
| /api/publications/{id} | DELETE |

### Inventory
| Endpoint | Method |
|--------|--------|
| /api/inventory | GET |
| /api/inventory/{publicationId}/adjust | POST |
| /api/inventory/adjustments | GET |

## Authentication Endpoints
| Endpoint | Method | Description |
|--------|--------|-------------|
| /api/auth/login | POST | Authenticate user and return JWT |
| /api/auth/register | POST | Register user and return JWT |
| /api/auth/refresh | POST | Refresh access token |

## Notes
- All endpoints are protected by role-based access control (RBAC).
- Inventory adjustments are atomic and audited.
- Orders cannot be modified after completion or cancellation.
- External integrations are isolated via the integration bridge layer.
- OAuth support is planned but not yet enabled.
