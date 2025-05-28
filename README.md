# Articles Management with Thymeleaf

A simple Spring Boot web application to manage and display articles, using Thymeleaf as the template engine and PostgreSQL as the database.

## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [How to Test](#how-to-test)
- [Important Configurations](#important-configurations)
- [Contributions](#contributions)
- [License](#license)

## Description

This project provides a web interface for listing and adding articles. It leverages Spring Boot for backend, Thymeleaf for server-side rendering, and PostgreSQL for data persistence.

## Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Maven
- Mockito & JUnit 5 (for unit testing)

## Prerequisites

- Java 17 or higher installed
- Maven 3.6 or higher installed
- PostgreSQL database configured and running

## How to Run

1. Clone the repository:

   ```bash
   git clone <REPOSITORY_URL>
   cd articles-thymeleaf

2. Configure the database properties in

 ```src/main/resources/application.properties ou application.yml.   ```

3. Run the project using Maven:

  ```mvn clean install```
  ```mvn spring-boot:run```

4. Access the application in your browser at:

http://localhost:8080/v1/articles

## How to Test

To run unit tests and generate the coverage report:

 ```mvn clean verify```

## Important Configurations

1) The Thymeleaf template files are located in src/main/resources/templates.
2) Static resources (CSS, JS) are in src/main/resources/static.
3) Database credentials and URL are set in application.properties or application.yml.
4) To enable Hibernate to update the schema automatically, set spring.jpa.hibernate.ddl-auto=update in your properties.

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.
