# Simple Contact List Application

This is a simple contact list application built using the Spring Boot framework version 3.2.2. It provides RESTful APIs for managing contacts and implements JWT (JSON Web Token) authentication for API access.

## Technologies Used

- Spring Boot 3.2.2
- MySQL 8.0.36
- Apache Maven 3.9.5 

## Frontend Integration

The frontend of this application is built using React.js and is available in a separate repository:

[Contact List Frontend Repository](https://github.com/shillari/contactlistfrontend)

## Setup Instructions

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/SpringBootExample.git


2. Navigate to the project directory:
   ```bash
   cd SpringBootExample


3. Configure MySQL database:

  - Install MySQL 8.0.36
  - Create a new database named jwt_credential
  - Update application.properties with your MySQL database configuration

 4. Build and run the application:

     ```bash
     ./mvnw spring-boot:run
