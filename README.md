# Student Management System API

## Overview

Student Management System API is a RESTful backend application built using Spring Boot and PostgreSQL. The application provides functionality for managing student records, course assignments, enrollment information, academic performance, and student status through a set of REST endpoints.

The project demonstrates backend software engineering concepts including layered architecture, dependency injection, data validation, exception handling, object-relational mapping, and database persistence.


## Features

* Create student records
* Retrieve all students
* Retrieve student details by ID
* Update student information
* Delete student records
* Assign students to courses
* Validate student data before persistence
* Manage enrollment information
* Track academic performance through average marks
* Maintain student status information



## Technologies Used

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Build Tool

* Maven

### Testing

* Postman

### Version Control

* Git
* GitHub



## Architecture

The application follows a layered architecture:

```text
Controller Layer
       ↓
Service Layer
       ↓
Repository Layer
       ↓
PostgreSQL Database
```

### Controller Layer

Handles HTTP requests and returns JSON responses.

### Service Layer

Contains business logic and validation rules.

### Repository Layer

Provides database access through Spring Data JPA.

### Entity Layer

Maps Java objects to database tables.

---

## Student Information Managed

* Student ID
* First Name
* Surname
* Contact Number
* Email Address
* Course
* Enrollment Date
* Academic Status
* Average Mark



## API Endpoints

| Method | Endpoint       | Description                |
| ------ | -------------- | -------------------------- |
| POST   | /students      | Create a new student       |
| GET    | /students      | Retrieve all students      |
| GET    | /students/{id} | Retrieve a student by ID   |
| PUT    | /students/{id} | Update student information |
| DELETE | /students/{id} | Delete a student record    |

---

## Validation Rules

* First name is required
* Surname is required
* Contact number is required
* Email must be valid
* Course assignment is required
* Enrollment date cannot be in the future
* Average mark must be between 0 and 100
* Status must be provided

---

## Key Concepts Demonstrated

* Object-Oriented Programming (OOP)
* RESTful API Development
* Dependency Injection
* Spring Boot Framework
* PostgreSQL Integration
* Spring Data JPA
* Entity Relationships
* Data Validation
* Exception Handling
* CRUD Operations
* Layered Architecture
* JSON Serialization

---

## Future Enhancements

* JWT Authentication and Authorization
* Swagger/OpenAPI Documentation
* Unit and Integration Testing
* Docker Containerization
* Cloud Deployment
* Student Performance Analytics Dashboard

---

## Author

Unity Montjane

Aspiring Software Engineer and Data Analyst passionate about building scalable backend applications using Java, Spring Boot, and PostgreSQL.

