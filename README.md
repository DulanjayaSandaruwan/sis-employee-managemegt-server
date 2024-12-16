# Employee Management System

This is a Spring Boot application designed to manage employee records. It allows for CRUD operations on employee data and includes features for managing employee details.
## Prerequisites

Before running the project, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+ (or use Spring Boot’s embedded Maven plugin)
- MySQL 8.0 or higher

## Project Setup

### 1. Clone the repository

```bash
git clone https://github.com/DulanjayaSandaruwan/sis-employee-managemegt-server.git
```

### 2. Set up MySQL Database

### 1. Configure Application Properties

In the `src/main/resources/application.yml` file, configure the MySQL database connection:

```properties
spring:
application:
name: employee-management-server
datasource:
url: jdbc:mysql://localhost:3306/employee-management-db?createDatabaseIfNotExist=true
username: your-username
password: your-password
driver-class-name: com.mysql.cj.jdbc.Driver
jpa:
hibernate:
ddl-auto: update
show-sql: true
properties:
hibernate:
format_sql: true
database-platform: org.hibernate.dialect.MySQL8Dialect
```

Replace `your-username` and `your-password` with the MySQL credentials you created.

### 4. Build and Run the Application

1. **Build the project** using Maven:

   ```bash
   mvn clean install
   ```

2. **Run the Spring Boot application**:

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

### 5. Accessing the Application

Once the application is running, you can access the following endpoints:

- **GET /employee**: Get all employees
- **POST /employee**: Add a new employee
- **GET /employee/{id}**: Get an employee by ID
- **PUT /employee/{id}**: Update an employee by ID
- **DELETE /employee/{id}**: Delete an employee by ID

  ```properties
  server.port=8080
  ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This README provides instructions for setting up the database, configuring the application, and running it locally. Make sure to replace placeholders like `your-username` and `your-password` with your actual credentials.