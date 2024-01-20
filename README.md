# Books Management
This project aims to store books added by users and use the Google Book API, in order to add books to the database more easily and quickly. Administrator can approve books, modify and delete. The application includes the ability to sing up new user and check their books with inforamation on whether they have been approved by the administrator.

## Table of contents
* [Technologies](#Technologies)
* [Functionalities of Book Management App](#Functionalities-of-Book-Management-App)
* [Setup](#Setup)
* [Screenshots](#Screenshots)

## Technologies
This is a web application built with:
* Java 20
* Spring Boot 3.1.0
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* Thymeleaf
* HTML
* CSS
* Bootstrap

## Functionalities of Book Management App
* Login
* Registration
* Adding a book
* Viewing books
* Adding books with Google Books API

## Setup
Steps to launch this project.
```bash
git clone https://github.com/Milosz-Szymczak/BooksManagement.git
cd BooksManagement
./mvnw package
java -jar target/*.jar
```
You can then access the Books Management at http://localhost:8080/.

#### Administrator login details:
Login: admin <br/>
Password: password

## Screenshots

### View for not logged in:
![visitor](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/c08c1c64-aba1-46e7-b68e-0432baf29c46)


### User view:

#### Search Book - Google Book API
![google_book](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/5706a397-626c-4121-a0c4-ef20ba88bbe8)


#### Add Book 
![add_book](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/ed249029-fba9-4eee-9100-1a755b8b7949)

### User profile
![my_profile](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/29711560-a1f1-49e4-bebb-edfd07d392ff)


### Administrator view:

#### Management Books
![management_books](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/727c56d0-9c1c-4d14-bedb-ff88d6bf1771)


#### Books to confirm
![books_to_confirm](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/5481d7e0-92e2-4072-8268-fe4c409c7615)

