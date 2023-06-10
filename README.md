## Meme website "Memeoven"

This project is a website built using Spring Boot, Spring Data JPA, HTML, CSS, Bootstrap and JavaScript with a Java backend.

## Project Description

The project aims to create a modern and responsive website with meme using the Spring Boot framework for the backend and HTML, CSS, and Bootstrap for the frontend. The website will serve as a platform for users to interact with various features and functionalities provided by the application.

## Features

- User registration and login
- User profile management (avatar change, profile description, date of birth, gender)
- Meme upload (image, name and category)
- User interaction with meme (comment, save to favorites, like)
- Delete functionality for user-authors(delete meme, delete comment)

## Technologies Used

- Spring Boot
- Spring Data JPA
- HTML
- CSS
- JavaScript
- Bootstrap
- Java

## Prerequisites

Make sure you have the following installed on your system:

- Java Development Kit (JDK) 17 or later
- Apache Maven
- IDE (Eclipse, IntelliJ IDEA, or any other Java IDE)
- MySQL Server 8.0 or later

## Getting Started

Follow these steps to get the project up and running on your local machine:

1. Clone the repository: `git clone https://github.com/Dantesuomi/Memeoven`
2. Open the project in your IDE.
3. Build the project using Maven: mvn clean install
4. Run the application
5. Open a web browser and visit http://localhost:8080 to access the website.

## Configuration

The application requires certain configurations to be set up. Update the following properties in the application.properties file:

```
spring.datasource.url
spring.datasource.password
spring.datasource.username
```

## Folder Structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── memeoven
│   │   │           └── memeoven
│   │   │               ├── comment
│   │   │               ├── configuration
│   │   │               ├── exceptions
│   │   │               ├── meme
│   │   │               ├── storage
│   │   │               └── user
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       │   ├── avatars
│   │       │   ├── css
│   │       │   ├── favicon.ico
│   │       │   ├── images
│   │       │   ├── memes
│   │       │   └── script
│   │       └── templates
│   │           └── fragments
│   └── test
│       └── java
│           └── com
│               └── memeoven
│                   └── memeoven
```
## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [HTML](https://www.w3.org/html/)
- [CSS](https://www.w3.org/Style/CSS/)
- [Bootstrap](https://getbootstrap.com/)
- [JavaScript](https://www.w3schools.com/js/)