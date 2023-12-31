package pl.milosz.booksmanagement.service;

import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryMapDto;

import java.io.IOException;
import java.util.List;


public interface GoogleBookService {
    List<BookEntryMapDto> getAllGoogleBooks(String title) throws IOException;

    BookDto sendGoogleBookToCheck(String key);

}
