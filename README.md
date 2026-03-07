# Brokerage CRM (Spring Boot REST API)

A simple real‑estate brokerage CRM API built with Spring Boot. It supports provinces/cities, agents, clients, properties, appointments, and transactions (with income distribution).

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Springdoc OpenAPI (Swagger UI)

## How To Run

1. Create a PostgreSQL database:

```sql
CREATE DATABASE brokerage_crm;
```

2. Update database credentials in `src/main/resources/application.properties` if needed.

3. Run the app:

```powershell
mvn clean spring-boot:run
```

## Swagger / API Docs

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Core Endpoints (Examples)

### Provinces & Cities

- `POST /api/v1/locations/provinces`
```json
{ "code": "RW-01", "name": "Kigali" }
```

- `POST /api/v1/locations/provinces/{provinceId}/cities`
```json
{ "name": "Gasabo" }
```

- `GET /api/v1/locations/hierarchy`

### Agents

- `POST /api/v1/agents`
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "profile": { "bio": "Senior broker", "licenseNumber": "LIC-12345" },
  "city": { "id": 1 }
}
```

### Clients

- `POST /api/v1/clients`
```json
{
  "name": "Jane Client",
  "email": "jane@example.com",
  "phone": "+250788000111",
  "agents": [ { "id": 1 } ]
}
```

### Properties

- `POST /api/v1/properties`
```json
{
  "title": "3BR Apartment",
  "price": 85000,
  "status": "AVAILABLE",
  "agent": { "id": 1 },
  "city": { "id": 1 }
}
```

### Appointments

- `POST /api/v1/appointments`
```json
{
  "type": "VIEWING",
  "status": "SCHEDULED",
  "scheduledAt": "2026-03-10T14:30:00",
  "notes": "Client wants to see the balcony",
  "property": { "id": 1 },
  "agent": { "id": 1 },
  "client": { "id": 1 }
}
```

### Transactions (Income Distribution)

- `POST /api/v1/transactions/sell`
```json
{
  "propertyId": 1,
  "salePrice": 100000
}
```

Commission logic:
- Agent share: `salePrice * 0.05`
- Company share: `salePrice * 0.02`

## Notes

- If you change the database name, update `spring.datasource.url` in `src/main/resources/application.properties`.
- All examples are also visible in Swagger UI with prefilled request bodies.
