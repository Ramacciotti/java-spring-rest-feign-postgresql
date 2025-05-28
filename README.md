# Employee CRUD

Employee management CRUD project developed with Spring Boot, Feign Client, and PostgreSQL.

## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [How to Test](#how-to-test)
- [Important Configurations](#important-configurations)
- [Test Coverage](#test-coverage)
- [Contributions](#contributions)
- [License](#license)

## Description

This project implements a CRUD system for employee management, using Spring Boot for the application, Feign Client for external API communication, and PostgreSQL as the database.

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
- PostgreSQL database configured and running

## How to Run

1. Clone the repository:

   ```bash
   git clone <REPOSITORY_URL>
   cd employee-crud
   ```

2. Configure the database properties in

 ```src/main/resources/application.properties ou application.yml.   ```

3. Run the project using Maven:

  ```mvn clean install```
  ```mvn spring-boot:run```

## How to Test

To run unit tests and generate the coverage report:

 ```mvn clean verify```

## Important Configurations

1) The JaCoCo plugin is configured to exclude DTO, model, and exception classes from coverage since they are POJOs without logic to test.
2) To modify exclusions or JaCoCo settings, check the pom.xml file.
3) Set database credentials and URL in application.properties or application.yml.

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.
