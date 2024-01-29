package pl.milosz.booksmanagement.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.repository.BookRepository;
import pl.milosz.booksmanagement.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

@Service
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getBooksByKind(Kind kind) {
        return bookRepository.findAll().stream()
                .map(this::mapToBookDto)
                .filter(book -> book.getKind() == kind)
                .collect(Collectors.toList());
    }

    @Override
    public void saveBook(BookDto bookDto) {
        bookDto.setConfirm(false);
        Book book = mapToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> getBooksNotConfirm() {
        List<Book> allBooks = bookRepository.getBooksNotConfirm();

        return allBooks.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getConfirmBooks() {
        List<Book> allBooks = bookRepository.getConfirmBooks();

        return allBooks.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            return mapToBookDto(bookOptional.get());
        } else {
            throw new NoSuchElementException("Book not found with ID: " + id);
        }
    }

    @Override
    @Transactional
    public void updateBook(Long id, BookDto bookDto) {
        Book book = mapToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void confirmBook(Long id) {
        BookDto existBookDto = getBookById(id);
        existBookDto.setConfirm(true);

        Book book = mapToBook(existBookDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        BookDto bookDtoById = getBookById(id);

        Book book = mapToBook(bookDtoById);
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> getBooksAddedByUser(String username) {
        List<BookDto> allBooks = new ArrayList<>();
        List<BookDto> confirmBooks = getConfirmBooks().stream().filter(bookDto -> bookDto.getUser().getUsername().equals(username)).toList();
        List<BookDto> booksNotConfirm = getBooksNotConfirm().stream().filter(bookDto -> bookDto.getUser().getUsername().equals(username)).toList();
        allBooks.addAll(confirmBooks);
        allBooks.addAll(booksNotConfirm);

        return allBooks;
    }

    private BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .imageLink(book.getImageLink())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .author(book.getAuthor())
                .kind(book.getKind())
                .releaseDate(book.getReleaseDate())
                .isbn(book.getIsbn())
                .language(book.getLanguage())
                .confirm(book.isConfirm())
                .user(book.getUser())
                .build();
    }

    private Book mapToBook(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .imageLink(bookDto.getImageLink())
                .title(bookDto.getTitle())
                .publisher(bookDto.getPublisher())
                .author(bookDto.getAuthor())
                .kind(bookDto.getKind())
                .releaseDate(bookDto.getReleaseDate())
                .isbn(bookDto.getIsbn())
                .language(bookDto.getLanguage())
                .confirm(bookDto.isConfirm())
                .user(bookDto.getUser())
                .build();
    }

}
