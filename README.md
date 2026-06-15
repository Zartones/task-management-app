# Task Management System
Manage tasks and projects effectively through a web-based application. This system enables task creation, assignment, progress tracking, and completion.


## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Web
* Docker
* Swagger
* MySQL
* Liquibase
* Maven
##  Entities
* User: Contains information about the registered user including their authentication details and personal information.
* Role: Represents the role of a user in the system, for example, admin or user.
* Project: Represents the project created by users
* Task: Represents a task within a project
* Comment: Represents a comment left by user under a task
* Attachment: Represents an attachment that was added to task using dropbox services
* Label: Represents a label given by user to a task
## Project Structure

```text    
src/
├── main/
│   ├── java/
│   │   └── online/taskmanagementapp/
│   │       ├── annotation/       # Custom annotations
│   │       ├── config/           # General application & framework configurations
│   │       ├── controller/       # REST API Controllers
│   │       ├── dto/              # Data Transfer Objects
│   │       │   ├── attachment/
│   │       │   ├── comment/
│   │       │   ├── label/
│   │       │   ├── project/
│   │       │   ├── task/
│   │       │   └── user/
│   │       ├── exception/        # Custom exceptions and global exception handling
│   │       ├── mapper/           # Object mapping logic 
│   │       ├── models/           # Database entities 
│   │       ├── repository/       # Spring Data JPA Repositories 
│   │       ├── security/         # Authentication and authorization configuration 
│   │       ├── service/          # Business logic layer interfaces and implementations
│   │       ├── validator/        # Custom validation logic and constraint validators
│   │       └── TaskManagementAppApplication.java  # Main application entry point
│   └── resources/
│       ├── db.changelog/         # Liquibase database migration scripts
│       ├── application.properties # Main application settings
│       └── liquibase.properties   # Liquibase migration configuration environment properties
└── test/                         # Unit and integration tests
```  

## Getting Started

### Prerequisites

Make sure you have the following installed:

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/)

---

### 1. Clone the repository

```bash
git clone https://github.com/Zartones/task-management-app
cd task-management-app
```

---

### 2. Configure environment variables

Create a `.env` using `.env.template` file in the project root:

```env
MYSQLDB_ROOT_PASSWORD=your_root_password
MYSQLDB_DATABASE=mysql_db
MYSQLDB_USER=user
MYSQLDB_PASSWORD=your_db_password
MYSQLDB_LOCAL_PORT=3306
MYSQLDB_DOCKER_PORT=3306

SPRING_LOCAL_PORT=8088
SPRING_DOCKER_PORT=8080
DEBUG_PORT=5005
```

---

### 3. Build the project

```bash
mvn clean package -DskipTests
```

---

### 4. Run with Docker Compose

```bash
docker-compose up --build
```

This will:
- Pull and start a **MySQL** container
- Wait for the database to become healthy before starting the app
- Inject datasource config into Spring Boot via `SPRING_APPLICATION_JSON`
- Run **Liquibase** migrations automatically on startup
- Start the **Spring Boot** application
- Expose a remote **debug port** (5005) for IDE debugging

To stop the containers:

```bash
docker-compose down
```

To stop and remove all volumes (wipes the database):

```bash
docker-compose down -v
```

---

### 5. Access the application

| Service    | URL                                         |
|------------|---------------------------------------------|
| REST API   | http://localhost:8088                       |
| Swagger UI | http://localhost:8088/swagger-ui/index.html |
| MySQL      | localhost:3306                              |
| Debug port | 5005                                        |

---
## Contacts
**Email:** antchayka@gmail.com