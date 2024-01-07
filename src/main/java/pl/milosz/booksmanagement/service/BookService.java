package pl.milosz.booksmanagement.service;

import pl.milosz.booksmanagement.dto.BookDto;

import java.util.List;


public interface BookService {
    void saveBook(BookDto bookDto);

    List<BookDto> getBooksNotConfirm();
    List<BookDto> getConfirmBooks();

    BookDto getBookById(Long id);

    void updateBook(Long id, BookDto bookDto);
    void confirmBook(Long id);

    void deleteBook(Long id);
}
