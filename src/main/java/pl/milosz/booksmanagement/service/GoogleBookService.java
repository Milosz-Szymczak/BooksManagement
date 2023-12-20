package pl.milosz.booksmanagement.service;

import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;

import java.io.IOException;
import java.util.Map;


public interface GoogleBookService {
    Map<String, GoogleBookDto> getAllGoogleBooks(String title) throws IOException;
}
