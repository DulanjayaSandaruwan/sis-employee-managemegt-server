# Employee Management System

This is a Spring Boot application designed to manage employee records. It allows for CRUD operations on employee data and includes features for managing employee details, roles, and departments.

## Prerequisites

Before running the project, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+ (or use Spring Boot’s embedded Maven plugin)
- MySQL 8.0 or higher

## Project Setup

### 1. Clone the repository

```bash
git clone https://github.com/your-username/employee-management-system.git
cd employee-management-system
```

### 2. Set up MySQL Database

1. **Create a database** in MySQL for the application:

   ```sql
   CREATE DATABASE employee_management;
   ```

2. **Configure MySQL user permissions** (optional but recommended for security):

   ```sql
   CREATE USER 'your-username'@'localhost' IDENTIFIED BY 'your-password';
   GRANT ALL PRIVILEGES ON employee_management.* TO 'your-username'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Create the required tables**:
   The schema will be automatically created by Hibernate (JPA) when you run the application. Ensure that the user you created has the necessary privileges to create tables in the database.

### 3. Configure Application Properties

In the `src/main/resources/application.properties` file, configure the MySQL database connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&serverTimezone=UTC
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
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

- **GET /employees**: Get all employees
- **POST /employees**: Add a new employee
- **GET /employees/{id}**: Get an employee by ID
- **PUT /employees/{id}**: Update an employee by ID
- **DELETE /employees/{id}**: Delete an employee by ID

You can test these endpoints using tools like [Postman](https://www.postman.com/) or by directly using `curl`.

### 6. Database Schema

The application will automatically create the necessary database schema on startup if `spring.jpa.hibernate.ddl-auto=update` is set. The main entities are:

- **Employee**: Represents an employee with details such as name, role, department, etc.
- **Department**: Represents a department within the organization.
- **Role**: Represents the role of an employee (e.g., Manager, Developer).

## Testing the Application

Unit and integration tests are included in the project. You can run the tests with Maven:

```bash
mvn test
```

## Troubleshooting

- **Database Connection Issues**: Ensure MySQL is running and the connection details in `application.properties` are correct.
- **Table Creation Fails**: Ensure that the MySQL user has proper permissions to create tables.
- **Port Conflicts**: If port 8080 is already in use, change the port in `application.properties`:

  ```properties
  server.port=8081
  ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This README provides instructions for setting up the database, configuring the application, and running it locally. Make sure to replace placeholders like `your-username` and `your-password` with your actual credentials.