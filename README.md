# Brokerage CRM (Spring Boot REST API)

A simple real-estate brokerage CRM API built with Spring Boot. It supports provinces/districts/sectors/villages, agents, clients, properties, appointments, and transactions (with income distribution).

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Springdoc OpenAPI (Swagger UI)

## Dependencies Used

- `spring-boot-starter-webmvc`
- `spring-boot-starter-data-jpa`
- `postgresql`
- `springdoc-openapi-starter-webmvc-ui` (Swagger UI)
- `lombok` (used to reduce boilerplate in entities)

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

### Locations (Hierarchy)

- `POST /api/v1/locations/provinces`
```json
{ "code": "RW-01", "name": "Kigali" }
```

- `POST /api/v1/locations/provinces/{provinceId}/districts`
```json
{ "name": "Gasabo" }
```

- `POST /api/v1/locations/districts/{districtId}/sectors`
```json
{ "name": "Kinyinya" }
```

- `POST /api/v1/locations/sectors/{sectorId}/villages`
```json
{ "name": "Gasharu" }
```

- `GET /api/v1/locations/hierarchy`

### Agents

- `POST /api/v1/agents`
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "profile": { "bio": "Senior broker", "licenseNumber": "LIC-12345" },
  "village": { "id": 1 }
}
```

- `GET /api/v1/agents?page=0&size=10&sort=name,asc`

- `GET /api/v1/agents/by-province?code=RW-03`
- `GET /api/v1/agents/by-province?name=Northern`

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
  "village": { "id": 1 }
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

## Assessment Mapping (Logic Explanation)

### 1. ERD with at least five tables

- Tables used: `provinces`, `districts`, `sectors`, `villages`, `agent`, `agent_profile`, `properties`, `clients`, `appointments`, `transactions`.
- The core location hierarchy is `Province ? District ? Sector ? Village`.

### 2. Saving Location

- Locations are saved through nested endpoints in `LocationController`.
- Each child entity is saved with its parent ID, and the service validates the parent exists before saving.

### 3. Sorting and Pagination

- Sorting and pagination are handled by Spring Data `Pageable` in `AgentController`.
- Example: `GET /api/v1/agents?page=0&size=10&sort=name,asc`.
- Pagination limits results per page and improves performance by avoiding large full-table fetches.

### 4. Many-to-Many

- `Agent` ? `Client` is Many-to-Many via join table `agent_clients`.
- JPA maps this using `@ManyToMany` and `@JoinTable` in `Agent`.

### 5. One-to-Many

- `Province` ? `District` ? `Sector` ? `Village` is One-to-Many on each step.
- Each child has a foreign key to its parent (e.g., `districts.province_id`).

### 6. One-to-One

- `Agent` ? `AgentProfile` is One-to-One using `@OneToOne` with `profile_id`.

### 7. existsBy() Usage

- Examples: `existsByEmail` in `AgentRepository`, `existsByNameAndProvinceId` in `DistrictRepository`.
- Used to prevent duplicates before saving.

### 8. Retrieve users by province code or name

- Endpoint: `GET /api/v1/agents/by-province?code=RW-03` or `?name=Northern`.
- Repository method joins `Agent ? Village ? Sector ? District ? Province` and filters by code or name.

## Notes

- If you change the database name, update `spring.datasource.url` in `src/main/resources/application.properties`.
- All examples are also visible in Swagger UI with prefilled request bodies.

## Database Test Queries (pgAdmin)

```sql
SELECT * FROM provinces;
SELECT * FROM districts;
SELECT * FROM sectors;
SELECT * FROM villages;
```

```sql
SELECT
  p.name AS province,
  d.name AS district,
  s.name AS sector,
  v.name AS village
FROM villages v
JOIN sectors s ON v.sector_id = s.id
JOIN districts d ON s.district_id = d.id
JOIN provinces p ON d.province_id = p.id
ORDER BY p.name, d.name, s.name, v.name;
```

```sql
SELECT
  a.name AS agent,
  a.email,
  v.name AS village,
  s.name AS sector,
  d.name AS district,
  p.name AS province
FROM agent a
JOIN villages v ON a.village_id = v.id
JOIN sectors s ON v.sector_id = s.id
JOIN districts d ON s.district_id = d.id
JOIN provinces p ON d.province_id = p.id
ORDER BY a.name;
```

```sql
SELECT
  pr.title,
  pr.price,
  pr.status,
  v.name AS village,
  s.name AS sector,
  d.name AS district,
  p.name AS province
FROM properties pr
JOIN villages v ON pr.village_id = v.id
JOIN sectors s ON v.sector_id = s.id
JOIN districts d ON s.district_id = d.id
JOIN provinces p ON d.province_id = p.id
ORDER BY pr.title;
```
