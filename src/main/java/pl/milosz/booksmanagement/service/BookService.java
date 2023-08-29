package pl.milosz.booksmanagement.service;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.Book;

import java.util.List;

@Service
public interface BookService {
    Book saveBook(Book book);

    List<Book> getBooksWithoutConfirm();
    List<Book> getBooksWithConfirm();

    Book getBookById(Long id);

    Book updateBook(Book book);

    void deleteBook(Long id);
}
