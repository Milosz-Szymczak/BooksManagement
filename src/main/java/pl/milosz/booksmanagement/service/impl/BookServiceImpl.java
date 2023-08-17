package pl.milosz.booksmanagement.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.repository.BooksRepository;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BooksRepository booksRepository;

    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book saveBook(Book book) {
        return booksRepository.save(book);
    }

    @Override
    public List<Book> getAllBook() {
        return booksRepository.findAll();
    }


}
