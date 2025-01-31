
<p align="center">
<img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=In%20development&color=GREEN&style=for-the-badge"/>
</p>

### Prerequisites ⚙️

- **Java 17 or higher** ☕
- **MongoDB** installed locally or on a remote server 🌍

### Steps to Run the Application Locally 💻

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/your-repository.git
    cd your-repository
    ```

2. Open the `application.properties` file and configure your MongoDB credentials:

    ```properties
    spring.data.mongodb.uri=mongodb://your-username:password@localhost:27017/your-database
    ```

3. Compile the project with Maven:

    ```bash
    mvn clean install
    ```

4. Run the project:

    ```bash
    mvn spring-boot:run
    ```

5. Access the application in your browser (by default, it will be available at `http://localhost:8080`).

## Endpoints 📡

### `POST /auth/login`
- **Description**: Logs in a user with email and password.
- **Request Body**:

    ```json
    {
        "email": "user@example.com",
        "password": "password123"
    }
    ```

- **Response**:

    ```json
    {
        "name": "User Name",
        "token": "jwt_token_here"
    }
    ```

### `POST /auth/register`
- **Description**: Registers a new user.
- **Request Body**:

    ```json
    {
        "email": "user@example.com",
        "password": "password123",
        "name": "User Name"
    }
    ```

- **Response**:

    ```json
    {
        "name": "User Name",
        "token": "jwt_token_here"
    }
    ```

### `GET /auth/visitor`
- **Description**: Allows a user to enter as a visitor.
- **Response**:

    ```json
    {
        "visitorId": "temporary_generated_id"
    }
    ```

## Contributing 🤝

1. Fork the project.
2. Create a branch for your feature (`git checkout -b feature/new-feature`).
3. Make your changes and commit (`git commit -am 'Add new feature'`).
4. Push your branch to the remote repository (`git push origin feature/new-feature`).
5. Open a Pull Request.

## License 📜

All rights reserved [Nicolas Alves©](https://www.linkedin.com/in/nicolasdevback)
