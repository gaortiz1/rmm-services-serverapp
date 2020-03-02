# Rmm Services 

Store devices and services for a customer and calculates monthly cost operations.

Basic Authentication:

    user: admin
    password: admin

Swagger UI : http://localhost:8080/swagger-ui.html
## Getting Started

These instructions allow you to run a copy of the running project on your local machine for development and testing purposes.
### Prerequisites

- Java 8
- Lombok
- Postgresql (create this database **rmm-services**)

### Installing

**Note:** Before compilation, you might configure your IDE with Lombok plugin

Compilation

```
./gradlew build
```

Running locally

```
./gradlew bootRun
```

**Note:** DB migrations will be run using Liquibase project and will be executed when the project start


## Running the test cases

Run the test cases execute

```
./gradlew test
```

## Customization

For customization you can add environment variables according to:

| Name  | Description  | Default  |
|--------|-------------|----------|
| PG_HOST | Postgres Hostname | `localhost` |
| PG_DATABASE | Postgres Database Name | production: `services` test: `services_test` |
| PG_USERNAME | Postgres username | `postgres` |
| PG_PASSWORD | Postgres Password | `postgres` |



## Build With

- SpringBoot
- Spring Framework (Core, Data, MVC, JPA, Securiry)
- Lombok
- Liquibase
- JUnit 5 Jupiter
- Mockito
- Gradle 5

## Architecture
- DDD domain driven design
- CQRS stands for Command Query Responsibility Segregation
- Clean Architecture 

## Author

- Gabriel Ortiz - (https://github.com/gaortiz1)