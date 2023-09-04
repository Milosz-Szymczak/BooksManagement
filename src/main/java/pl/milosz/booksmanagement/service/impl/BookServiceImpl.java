package pl.milosz.booksmanagement.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.repository.BooksRepository;
import pl.milosz.booksmanagement.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BooksRepository booksRepository;


    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        bookDto.setConfirm(false);
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
        booksRepository.save(book);
        return bookDto;
    }

    @Override
    public List<BookDto> getBooksWithoutConfirm() {
        List<Book> allBooks = booksRepository.findAll();

        List<BookDto> allBooksDto = allBooks.stream()
                        .filter(book ->!book.isConfirm())
                        .map(this::mapToBookDto)
                        .collect(Collectors.toList());

        return allBooksDto;
    }

    @Override
    public List<BookDto> getBooksWithConfirm() {
        List<Book> allBooks = booksRepository.findAll();

        List<BookDto> allBooksDto = allBooks.stream()
                .filter(Book::isConfirm)
                .map(this::mapToBookDto)
                .collect(Collectors.toList());

        return allBooksDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = booksRepository.findById(id).get();
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        booksRepository.save(book);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        BookDto bookById = getBookById(id);
        Book book = new Book();
        BeanUtils.copyProperties(bookById, book);
        booksRepository.delete(book);
    }

    private BookDto mapToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setKind(book.getKind());
        bookDto.setReleaseDate(book.getReleaseDate());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setConfirm(book.isConfirm());
        return bookDto;
    }
}
