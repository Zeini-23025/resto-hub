# Menu Service – Restaurant Management System

##  Description
The **menu-service** is a RESTful microservice responsible for managing restaurant menu items.
It provides full CRUD operations and exposes a documented REST API using OpenAPI/Swagger.

This service is part of a microservices-based restaurant management system and focuses **only**
on menu management (items, prices, availability), without handling orders or kitchen operations.

---

##  Architecture
The service follows a layered REST architecture:

- **Controller layer** – REST endpoints (HTTP requests/responses)
- **Service layer** – business logic and validation
- **Repository layer** – data access using Spring Data JPA
- **Model layer** – domain entities mapped to database tables

---

##  Technologies Used
- Java 17  
- Spring Boot 3.2.5  
- Spring Web  
- Spring Data JPA (Hibernate)  
- PostgreSQL  
- Bean Validation (Jakarta Validation)  
- OpenAPI / Swagger (springdoc-openapi)  
- Maven  

---

##  Configuration

### Server
The service runs on port **8082**.

```properties
server.port=8082
