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
* JUnit
* Mockito
* Thymeleaf
* HTML
* CSS
* Bootstrap
* Docker

## Functionalities of Book Management App
* Login
* Registration
* Adding a book
* Viewing books
* Filtering books by kind
* Adding books with Google Books API
* Administrator can approve books, modify and delete them

## Setup
Steps to launch this project.
```bash
git clone https://github.com/Milosz-Szymczak/BooksManagement.git
cd BooksManagement
mvnw package
docker-compose build
docker-compose up
```
You can then access the Books Management at http://localhost:8080/.

#### Administrator login details:
Login: admin <br/>
Password: password

## Screenshots

### View for not logged in:
![visitor](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/2a76293d-ff95-48be-a3cf-0fdf32a736aa)


### User view:

#### Search Book - Google Book API
![google_book](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/5706a397-626c-4121-a0c4-ef20ba88bbe8)


#### Add Book 
![add_book](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/ed249029-fba9-4eee-9100-1a755b8b7949)

### User profile
![my_profile](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/db5c5a0e-efc4-4231-83c2-290a6a921530)


### Administrator view:

#### Management Books
![management_books](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/21576014-654f-4659-8466-c5f74c879e95)


#### Books to confirm
![books_to_confirm](https://github.com/Milosz-Szymczak/BooksManagement/assets/99685108/a0d31920-9537-48bb-a3a3-a325681dec6a)


