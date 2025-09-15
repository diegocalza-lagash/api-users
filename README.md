# User API

A RESTful API for user management with JWT authentication.

## Architecture Overview

```mermaid
classDiagram
    class UserController {
        +registerUser(UserRequest): UserResponse
        +getUserProfile(): UserResponse
    }
    
    class UserService {
        -userRepository: UserRepository
        -tokenGenerator: TokenGenerator
        +createUser(UserRequest): User
        +getUserProfile(String): User
    }
    
    class UserRepository {
        +findByEmail(String): Optional~User~
        +save(User): User
    }
    
    class User {
        -id: UUID
        -name: String
        -email: String
        -password: String
        -created: LocalDateTime
        -modified: LocalDateTime
        -lastLogin: LocalDateTime
        -isActive: boolean
        -token: String
    }
    
    class UserRequest {
        +name: String
        +email: String
        +password: String
    }
    
    class UserResponse {
        +id: UUID
        +name: String
        +email: String
        +created: String
        +modified: String
        +lastLogin: String
        +isActive: boolean
        +token: String
    }
    
    class GlobalExceptionHandler {
        +handleEmailAlreadyExists(EmailAlreadyExistsException)
        +handleMethodArgumentNotValid(MethodArgumentNotValidException)
    }
    
    class SecurityConfig {
        +securityFilterChain(HttpSecurity)
    }
    
    UserController --> UserService
    UserService --> UserRepository
    UserService --> TokenGenerator
    UserRepository --> User
    UserController ..> UserRequest
    UserController ..> UserResponse
    GlobalExceptionHandler ..> ErrorResponse
    SecurityConfig --> JwtRequestFilter
```

## API Endpoints

### Register User
```
POST /api/users
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "SecurePass123"
}
```

### Get User Profile
```
GET /api/users/me
Authorization: Bearer <token>
```

## Validation Rules

- **Name**: 3-100 characters, letters and spaces only
- **Email**: Valid email format
- **Password**: 
  - At least one uppercase letter
  - At least one lowercase letter
  - At least two numbers
  - Only alphanumeric characters allowed

## Setup

1. Clone the repository
2. Configure the database in `application.properties`
3. Run the application:
   ```
   ./gradlew bootRun
   ```

## Testing

Run the tests with:
```
./gradlew test
```

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT Authentication
- H2 Database (for development)
- JPA/Hibernate
- JUnit 5
- Maven/Gradle