# Employee Management Application

## How to Run the Application Locally

To run the application locally, follow the steps outlined below:

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [JDK version 17+](https://www.oracle.com/java/technologies/downloads/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) or another Java IDE
- [Visual Studio Code](https://code.visualstudio.com/) or another ReactJS IDE

### Step 1: Unzip and Explore

1. Unzip `employee-management-app.zip`.
2. The extracted contents include two folders and two files:
    - `employee-management`: Java and Spring Boot application.
    - `employee-management-ui`: ReactJS with TypeScript application.
    - `data.txt`: Sample test data in JSON format.
    - `docker-compose.yml`: Docker Compose file for deploying the applications.

### Step 2: Set Up the Application

- **Java Application**
    - Import the `employee-management` project folder into your IDE.
    - Build the application with the command: `mvn clean install` to load dependencies and create the JAR file.

- **ReactJS Application**
    - Import the `employee-management-ui` project folder into your IDE.
    - Navigate to the `employee-management-ui` folder and run: `npm install` to load the dependencies.

### Step 3: Run the Application

- Navigate back to the root folder of your application (`employee-management-app`).
- Run the application with Docker Compose:
    - Execute the command: `docker compose up`.
    - Ensure that Docker is up and running on your local machine.

## How to Use the Application

- Once the backend service is up and running, you can use Postman or Swagger:
    - [Swagger UI](http://localhost:8080/swagger-ui/index.html) for exploring the available API endpoints and performing operations.
- Use the following URL to insert test data (data available in `data.txt`):

http
POST http://localhost:8080/api/employees/bulk-insert
## Available Backend Service URLs

Use the following URL to insert test data (data available in `data.txt`):

- **POST**: [http://localhost:8080/api/employees/bulk-insert](http://localhost:8080/api/employees/bulk-insert).

Other available backend service URLs:

- **GET**: [http://localhost:8080/api/employees](http://localhost:8080/api/employees) (List all employees).
- **GET**: [http://localhost:8080/api/employees/department/{department}](http://localhost:8080/api/employees/department/{department}) (List employees by department).
- **GET**: [http://localhost:8080/api/employees/salary/10000?condition=greaterThan](http://localhost:8080/api/employees/salary/10000?condition=greaterThan) (List employees with a salary greater than £10K).
- **GET**: [http://localhost:8080/api/employees/salary/10000?condition=lessThan](http://localhost:8080/api/employees/salary/10000?condition=lessThan) (List employees with a salary less than £10K).
- **GET**: [http://localhost:8080/api/employees/department](http://localhost:8080/api/employees/department) (List all departments).

All of the above features are available on the frontend service at:
- [http://localhost:3000/](http://localhost:3000/)# employee-management-app
