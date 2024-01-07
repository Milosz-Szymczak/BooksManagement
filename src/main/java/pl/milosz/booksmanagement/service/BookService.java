package pl.milosz.booksmanagement.service;

import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;

import java.util.List;


public interface BookService {
    BookDto saveBook(BookDto bookDto);

    List<BookDto> getBooksNotConfirm();
    List<BookDto> getConfirmBooks();

    BookDto getBookById(Long id);

    void updateBook(Long id, BookDto bookDto);
    void confirmBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
}
