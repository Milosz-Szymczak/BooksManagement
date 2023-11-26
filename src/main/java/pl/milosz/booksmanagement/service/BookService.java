package pl.milosz.booksmanagement.service;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.Book;

import java.util.List;

@Service
public interface BookService {
    BookDto saveBook(BookDto bookDto);

    List<BookDto> getBooksWithoutConfirm();
    List<BookDto> getBooksWithConfirm();

    BookDto getBookById(Long id);

    void updateBook(BookDto bookDto);

    void deleteBook(Long id);
}
