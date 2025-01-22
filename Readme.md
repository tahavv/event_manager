# EventManager Backend

A **Spring Boot** application that provides a secure, role-based system for managing events, users, and rooms. It uses JWT for authentication, with **ADMIN** and **MANAGER** roles having extended privileges. Features include user registration, OTP verification, password reset, Google OAuth2 login, account locking after multiple failed attempts, and more.

## Key Features

1. **User Registration & OTP Verification**
    - Users sign up and receive a one-time code via email.
    - The account is verified upon entering the correct OTP.

2. **JWT Authentication**
    - Generates JWT tokens for secure communication.
    - Roles (`ADMIN`, `MANAGER`, `USER`, etc.) embedded in the token.

3. **Google OAuth2 Login**
    - Users can optionally sign up or log in via Google.

4. **Account Locking & Login Attempts**
    - Locks the user after 5 incorrect login attempts.
    - Admin or Manager can unlock, or the user can reset password.

5. **Password Reset**
    - Forgot password endpoint: sends a reset link via email.
    - Resets password upon user confirmation.

6. **Role-Based Endpoints**
    - `ADMIN` and `MANAGER`: Full CRUD on events, rooms, users (with differences).
    - `USER`: Access to personal account, events listing, etc.

7. **CORS Configuration**
    - Allows requests from `http://localhost:4200` (or configured origins).
    - Spring Security integrates with global CORS.

8. **Event, Room & User Management**
    - Create, update, delete events and rooms (role-based).
    - Lock/unlock user accounts, retrieve user info.

## Tech Stack

- **Java 17+** / **Spring Boot**
- **PostgreSQL** (or any SQL DB, configurable in `application.properties`)
- **Hibernate** / **JPA** for ORM
- **Spring Security** + **JWT**
- **JavaMailSender** for emailing OTP/reset links
- **Maven** for build
- **Swagger/OpenAPI** for API documentation

## Project Structure (Highlights)

eventmanager-backend/ 
┣ src/main/java/com/eventmanager/ 
┃ ┣ config/ # SecurityConfig, WebConfig (CORS) 
┃ ┣ controller/ # REST Controllers (UserController, EventController, etc.) 
┃ ┣ dto/ # Request/Response Data Transfer Objects 
┃ ┣ model/ # JPA Entities (User, Event, Room, etc.)
┃ ┣ repository/ # Spring Data Repositories 
┃ ┣ services/ # Service layer (UserService, EventService, etc.)
┃ ┗ utils/ # JWT utility, etc. 
┣ src/main/resources/ 
┃ ┣ templates/ # HTML email templates (OTP, reset password) 
┃ ┗ application.properties 
┗ pom.xml


## Setup & Run

1. **Clone** the repository:
   ```bash
   git clone https://github.com/tahavv/event_manager.git
   cd eventmanager-backend

Configure DB & Secrets

In src/main/resources/application.properties, set:
properties
Copier le code
spring.datasource.url=jdbc:postgresql://localhost:5432/event_db
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASS
jwt.secret=YOUR_BASE64_ENCODED_SECRET
Adjust host/port, secret, etc. as needed.

Build & Run:

bash
Copier le code
mvn clean install
mvn spring-boot:run
The app starts on http://localhost:8083.

Swagger UI:

Visit http://localhost:8083/swagger-ui/index.html
Explore all APIs (secured endpoints require JWT in Authorization: Bearer <token>).
API Endpoints (Examples)
Auth
POST /api/auth/signup : Register new user (receives OTP).
POST /api/auth/signin : Login, returns JWT token.
POST /api/auth/forgot-password : Sends a reset link.
POST /api/auth/reset-password?token=XYZ : Resets password.
Users
GET /api/users (ADMIN only) : List all users.
PUT /api/users/{id} : Update user by ID.
DELETE /api/users/{id} : Delete user by ID.
Events
GET /api/events : Public listing of events.
POST /api/events/add (ADMIN, MANAGER) : Create new event.
DELETE /api/events/{id} (ADMIN) : Delete event.
Rooms
GET /api/rooms : List all rooms.
POST /api/rooms (ADMIN, MANAGER) : Create new room.
DELETE /api/rooms/{id} (ADMIN) : Delete room.
Contributing
Fork the repo & create a new branch for feature/fix.
Submit a Pull Request with a clear description.
Ensure code style & tests pass.
License
MIT License. See LICENSE for details.

Contact
Author: Your Name
Email: your.email@example.com
csharp
Copier le code
Thank you for checking out the EventManager Backend!
