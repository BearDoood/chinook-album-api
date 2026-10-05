# Chinook Album API

CST-339 Lab 2 Part 1. Spring Boot, Spring Data JPA and PostgreSQL.

## Run

1. Load the Chinook script into a database named `chinook`.
2. Set `DB_URL`, `DB_USER` and `DB_PASSWORD` if yours differ from the defaults in `application.properties`.
3. `mvn spring-boot:run`
4. Open http://localhost:8080 for the home page and http://localhost:8080/swagger-ui.html for Swagger.

## Test

`mvn test` runs the integration tests against the real database. Each test rolls back.

## Postman

Import `postman/chinook-album-api.postman_collection.json`.
Run Create album first, it stores the new id for Update and Delete.
