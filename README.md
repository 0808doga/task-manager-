# Task Manager

## Description
Task Manager is a backend for organising personal and team work: users create tasks, group them into projects and label them with categories.
Later labs add persistence in PostgreSQL, a service layer, validation and secured access per user.

**Main entities:** `Task`, `Project`, `Category`

## Technology Stack
- Java 21 (builds and runs on newer JDKs too)
- Spring Boot 4.1.1 (the 3.2.x line in the lab sheet is end-of-life and no longer offered by Spring Initializr)
- Maven (wrapper included: `./mvnw`)
- H2 Database (development)
- PostgreSQL (production)
- Lombok

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA (it imports `pom.xml` as a Maven project)
3. Run `TaskManagerApplication` (or `./mvnw spring-boot:run`)
4. Application starts on `http://localhost:8080`
5. H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, user `sa`, empty password)

Tests: `./mvnw test`

## API Endpoints

### Hello (test controller)
- `GET /api/hello` - Greeting
- `GET /api/hello/{name}` - Personal greeting
- `POST /api/hello` - Echo the request body

### Task Management
- `GET /api/tasks` - Get all tasks (optional filter `?completed=true|false`)
- `GET /api/tasks/{id}` - Get single task
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `PATCH /api/tasks/{id}` - Partially update task (only fields present in the body)
- `DELETE /api/tasks/{id}` - Delete task

### Project Management
- `GET /api/projects` - Get all projects
- `GET /api/projects/{id}` - Get single project
- `POST /api/projects` - Create new project
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

### Category Management
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get single category
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

A Postman collection with all requests is in `postman/task-manager-lab1.postman_collection.json`
(Postman → Import → select the file).

## Development Status
- [x] Lab 1: Project setup and basic REST endpoints
- [ ] Lab 2: Database integration
- [ ] Lab 3: Service layer
- [ ] Lab 4: DTOs and validation
- [ ] Lab 5: Security
- [ ] Lab 6: Additional features
- [ ] Lab 7: Defense

## Author
Doga
