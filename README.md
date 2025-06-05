# Employee CRUD

Employee management CRUD project developed with Spring Boot, Feign Client, and PostgreSQL.

![image](https://github.com/user-attachments/assets/b38212d6-c1f6-4544-9158-3f7ae67138d6)

## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Running the Project](#running-the-project)
- [Running with Docker](#running-with-docker)
- [Running CI/CD Pipeline](#running-cicd-pipeline)
- [How to Test](#how-to-test)
- [Environment Variables](#environment-variables)
- [Important Configurations](#important-configurations)
- [Contributions](#contributions)
- [License](#license)

## Description

This project implements a CRUD system for employee management, using Spring Boot for the application, Feign Client for external API communication, and PostgreSQL as the database.

It also includes Docker support for containerized deployment and a GitHub Actions pipeline for automated build and tests.

## Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- Feign Client
- PostgreSQL
- Maven
- JaCoCo (for test coverage)
- Mockito & JUnit 5 (for unit testing)

## Prerequisites

- Java 17 or higher installed
- Maven 3.6 or higher installed
- Docker and Docker Compose installed (for containerized execution)
- GitHub account (to use CI/CD pipeline with Secrets configured)

## How to Run

Docker allows you to run the entire application including the database in containers, isolating dependencies and environment setup.

1) Create a .env file in the project root (and add it to .gitignore) with:

```SPRING_DATASOURCE_USERNAME=postgres ```
```SPRING_DATASOURCE_PASSWORD=yourpassword ```

2) Run Docker Compose to build and start the app and PostgreSQL database:

```docker compose up --build```

3) Access the app at http://localhost:8080/swagger-ui/index.html

4) To stop and clean containers: 

```docker compose down -v```

## How to Test

To run unit tests and generate the coverage report:

 ```mvn clean verify```

## Important Configurations

1) The JaCoCo plugin is configured to exclude DTO, model, and exception classes from coverage since they are POJOs without logic to test.
2) To modify exclusions or JaCoCo settings, check the pom.xml file.
3) Dockerfile and Docker Compose files are provided for containerized builds and deployments.
4) GitHub Actions workflow is configured to build, test, and deploy using Docker Compose.

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.
