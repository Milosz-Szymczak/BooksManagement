package pl.milosz.booksmanagement.service;

import pl.milosz.booksmanagement.dto.BookDto;

import java.util.List;


public interface BookService {
    BookDto saveBook(BookDto bookDto);

    List<BookDto> getBooksNotConfirm();
    List<BookDto> getConfirmBooks();

    BookDto getBookById(Long id);

    void updateBook(BookDto bookDto);

    void deleteBook(Long id);
}
