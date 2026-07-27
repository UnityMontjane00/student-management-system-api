# Venus Student Management System

A full-stack student-record management application built with Spring Boot, PostgreSQL, and a vanilla HTML, CSS, and JavaScript interface.

Venus-SMS lets an administrator create, view, update, and delete student records. The backend owns the business rules: it assigns the enrollment date and calculates whether a student is passing from the average mark.

## Features

- Create, list, update, and delete student records
- Responsive browser interface served by Spring Boot
- PostgreSQL persistence with Spring Data JPA
- Server-side validation for names and average marks
- Structured validation and not-found error responses
- Automatic enrollment date using `LocalDate`
- Automatic passing status using a Java `Predicate<BigDecimal>`
- Git and GitHub version control

## Business Rules

| Rule | Behaviour |
| --- | --- |
| Student status | `true` only when `averageMark > 65`; `65` is not passing. |
| Enrollment date | Assigned automatically when the record is first persisted. |
| First name | Cannot be blank. |
| Surname | Cannot be blank. |
| Average mark | Must be between `0` and `100`, inclusive. |
| Server-owned fields | The client cannot set `id`, `status`, or `dateOfEnrollment`. |

## Technology Stack

- Java 25+ (the current Maven compiler target)
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA and Hibernate
- PostgreSQL
- Maven Wrapper
- HTML5, CSS3, and vanilla JavaScript

## Architecture

```text
Browser UI
    ↓ fetch()
StudentController
    ↓
StudentService
    ↓
StudentRepository
    ↓
PostgreSQL
```

The frontend is intentionally split by responsibility:

```text
src/main/resources/static
├── index.html       # Structure and accessible form/table markup
├── css/style.css    # Responsive visual design
└── js
    ├── api.js       # All HTTP calls to the REST API
    └── app.js       # Form state, rendering, edit, and delete behaviour
```

The backend uses request DTOs to accept client input and response DTOs to return public data. This prevents clients from setting database IDs, enrollment dates, or status values directly.

## Prerequisites

- JDK 25 or newer
- PostgreSQL
- Git

> The Maven Wrapper is included, so a global Maven installation is not required.

## Local Setup

### 1. Create the database

In pgAdmin's Query Tool, connected to the default `postgres` database, run:

```sql
CREATE DATABASE studentdb;
```

### 2. Create local application configuration

Copy the example configuration:

```powershell
Copy-Item src/main/resources/application.properties.example src/main/resources/application.properties
```

Open `src/main/resources/application.properties` and replace `CHANGE_ME` with your PostgreSQL password.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

`application.properties` is ignored by Git so local credentials are not committed. Never commit database passwords, API keys, or tokens.

### 3. Run the application

From the project root in PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Open the application at [http://localhost:8080](http://localhost:8080).

## REST API

| Method | Endpoint | Description | Success response |
| --- | --- | --- | --- |
| `POST` | `/students` | Create a student | `201 Created` |
| `GET` | `/students` | Retrieve all students | `200 OK` |
| `GET` | `/student/{id}` | Retrieve one student | `200 OK` |
| `PUT` | `/student/{id}` | Replace a student's editable details | `200 OK` |
| `DELETE` | `/student/{id}` | Delete a student | `204 No Content` |

### Create a student

`POST /students`

```json
{
  "name": "Dumisane",
  "surname": "Ngubane",
  "email_address": "dumisane@fmtali.com",
  "contact_number": "10111",
  "averageMark": 99
}
```

Example response:

```json
{
  "id": 1,
  "name": "Dumisane",
  "surname": "Ngubane",
  "email_address": "dumisane@fmtali.com",
  "contact_number": "10111",
  "averageMark": 99,
  "date_of_enrollment": "2026-07-27",
  "status": true
}
```

### Validation error response

Invalid requests return `400 Bad Request` with a consistent error format:

```json
{
  "timestamp": "2026-07-27T18:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Request validation failed",
  "path": "/students",
  "fieldErrors": {
    "averageMark": "Average mark must not exceed 100"
  }
}
```

## Project Structure

```text
src/main/java/com/example/venussms
├── config          # Passing-status predicate
├── controller      # HTTP request handling
├── dto             # Request and response contracts
├── entity          # JPA entities
├── exception       # Domain exception and API error handling
├── repository      # Spring Data JPA repositories
└── service         # Use cases and business rules
```

## Testing Checklist

After starting the application, verify the following in the browser or Postman:

1. Create a student with an average mark of `99`; status must be `true`.
2. Create a student with an average mark of `65`; status must be `false`.
3. Submit a blank first name; confirm a `400` response with a field error.
4. Update a student's average mark; confirm status is recalculated.
5. Delete a student; confirm it no longer appears in `GET /students`.

## Version Control

The project is tracked at [UnityMontjane00/student-management-system-api](https://github.com/UnityMontjane00/student-management-system-api).

Use focused commits to keep history meaningful:

```powershell
git status
git add .
git commit -m "docs: improve project README"
git push origin main
```

## Future Improvements

- Automated unit and integration tests
- OpenAPI / Swagger documentation
- Search, filtering, and pagination
- Authentication and authorization
- Database migrations with Flyway
- Docker-based local development

## Author

Unity Montjane
Aspiring Software Engineer and Data Analyst
