# se4801-assignment1-ATE-5576-14

## ShopWave Spring Boot Application

This project is the solution for the SE 4801 Enterprise Application Development Assignment 1.

### Prerequisites
- Java 21
- Maven (if you don't have it installed natively, you may need to install it from maven.apache.org or use an IDE like IntelliJ/Eclipse)

### How to Run Locally

You can start the Spring Boot application using Maven:

```shell
mvn spring-boot:run
```

Once started, the server will run on `http://localhost:8080`.

**H2 Console**:
You can view the in-memory database by navigating to:
`http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `(leave blank)`

### Testing
To run all the unit and integration tests (Service, Controller, Repository):
```shell
mvn clean test
```
