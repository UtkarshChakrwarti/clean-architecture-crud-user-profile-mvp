# HelloNITR Project

This is a Spring Boot application that provides a RESTful API for managing users.

## Technologies Used

- Java
- Spring Boot
- Maven

## Features

The application provides the following endpoints:

- `GET /api/users/{id}`: Get a user by their ID.
- `GET /api/users`: Get all users with pagination.
- `POST /api/users`: Create a new user.
- `PUT /api/users/{id}`: Update an existing user.
- `DELETE /api/users/{id}`: Delete a user.
- `GET /api/users/search`: Search users with pagination support 
  Case-insensitive searches for name, email, department, mobile number or 
  any other field write query for it nested in the repository
## Setup

To run this project, you need to have Java and Maven installed on your machine.

1. Clone the repository: `git clone https://github.com/yourusername/hellonitr.git`
2. Navigate to the project directory: `cd hellonitr`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## Testing

The project includes unit tests for the service and controller layers, using JUnit and Mockito.

To run the tests, use the following command: `mvn test`

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
