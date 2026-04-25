# Flight Booking System

A Spring Boot based backend application for managing flight search, booking, and passenger operations using REST APIs.  
This project is built with layered architecture and includes validation, exception handling, and relational database management.

## Features

- Search flights by source, destination, and travel date
- Book flights for passengers
- Manage passenger details
- View booking details
- REST API based backend
- Layered architecture using Controller, Service, and DAO layers
- Validation and global exception handling
- PostgreSQL database integration using JPA/Hibernate

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API
- PostgreSQL
- Maven
- Postman

## Project Structure

```bash
src
└── main
    ├── java
    │   └── com.yourpackage.flightbookingsystem
    │       ├── controller
    │       ├── service
    │       ├── dao
    │       ├── entity
    │       ├── dto
    │       ├── exception
    │       └── repository
    └── resources
        ├── application.properties
        └── static
