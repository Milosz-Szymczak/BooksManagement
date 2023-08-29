package pl.milosz.booksmanagement.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.repository.BooksRepository;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;

    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book saveBook(Book book) {
        book.setConfirm(false);
        return booksRepository.save(book);
    }

    @Override
    public List<Book> getBooksWithoutConfirm() {
        List<Book> allBooks = booksRepository.findAll();
        allBooks.removeIf(Book::isConfirm);
        return allBooks;
    }

    @Override
    public List<Book> getBooksWithConfirm() {
        List<Book> allBooks = booksRepository.findAll();
        allBooks.removeIf(book -> !book.isConfirm());
        return allBooks;
    }

    @Override
    public Book getBookById(Long id) {
        return booksRepository.findById(id).get();
    }

    @Override
    public Book updateBook(Book book) {
        return booksRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book bookById = getBookById(id);
        booksRepository.delete(bookById);
    }
}
