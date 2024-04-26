FROM openjdk:20-oracle
COPY target/booksmanagement.jar booksmanagement.jar
ENTRYPOINT ["java", "-jar", "booksmanagement.jar"]