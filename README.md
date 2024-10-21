# Maya E-Commerce App Spring Boot Backend

## How to run (Without taskfile)
1 - Create a postgres container using docker compose with 
doployment/docker-compose/infra.yml
- navigate root folder of project via terminal or cmd
- run the command "docker compose -f /deployment/docker-compose/infra.yml up -d"

2 - Start Backend App
- .\mvnw spring-boot:run  (for linux)
- mvnw.cmd spring-boot:run  (for windows)

## Tech
- Spring Boot Starter Web
- Spring Boot Starter Data Jpa
- Spring Boot Starter Test
- Flyway
- PostgreSQL
- Lombok


## History
- CrossOrigin annotation added to the product controller
- MockMvc tests of Product and ProductCategory controllers implemented.
- Taskfile.yml added to easily start,stop and restart infrastructure containers
- Docker compose file added in /deployment/docker-compose to create a postgres container
- Flyway migrations prepared
- Product and ProductCategory controllers created
- Product and ProductCategory repositories created
- Product and ProductCategory entities created


## To be fixed
- Service layer
- Pagination
- Add codeName to product category model for ui
- Custom response models 
- Custom exceptions 
- Global exception handler 
- SQL Query optimization by creating projection classes and custom repository methods
- SQL Transactions management
- Model and Entity VALIDATION
- HATEOAS
- Swagger OpenApi


## Refs
- Spring Boot: [https://docs.spring.io/spring-boot](https://docs.spring.io/spring-boot/)
- Taskfile: [https://taskfile.dev/](https://taskfile.dev/)
- Docker Compose: [https://docs.docker.com/compose/](https://docs.docker.com/compose/)