# Library Manager

## Description
Library Manager is a backend for a small library: it keeps a catalogue of books and their authors and a list of registered members.
Later labs add borrowing (loans), persistence in PostgreSQL and secured access for librarians.

**Main entities:** `Book`, `Author`, `Member` (planned: `Loan`)

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
3. Run `LibraryManagerApplication` (or `./mvnw spring-boot:run`)
4. Application starts on `http://localhost:8080`
5. H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, user `sa`, empty password)

Tests: `./mvnw test`

## API Endpoints

### Hello (test controller)
- `GET /api/hello` - Greeting
- `GET /api/hello/{name}` - Personal greeting
- `POST /api/hello` - Echo the request body

### Book Management
- `GET /api/books` - Get all books (optional filter `?available=true|false`)
- `GET /api/books/{id}` - Get single book
- `POST /api/books` - Create new book
- `PUT /api/books/{id}` - Update book
- `PATCH /api/books/{id}` - Partially update book (only fields present in the body)
- `DELETE /api/books/{id}` - Delete book

### Author Management
- `GET /api/authors` - Get all authors
- `GET /api/authors/{id}` - Get single author
- `POST /api/authors` - Create new author
- `PUT /api/authors/{id}` - Update author
- `DELETE /api/authors/{id}` - Delete author

### Member Management
- `GET /api/members` - Get all members
- `GET /api/members/{id}` - Get single member
- `POST /api/members` - Create new member
- `PUT /api/members/{id}` - Update member
- `DELETE /api/members/{id}` - Delete member

A Postman collection with all requests is in `postman/library-manager-lab1.postman_collection.json`
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
[Your Name]
