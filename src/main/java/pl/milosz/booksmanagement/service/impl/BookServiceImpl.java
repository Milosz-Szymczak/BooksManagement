package pl.milosz.booksmanagement.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.repository.BookRepository;
import pl.milosz.booksmanagement.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

@Service
class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(BookDto bookDto) {
        bookDto.setConfirm(false);
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
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
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(bookOptional.get(), bookDto);
            return bookDto;
        } else {
            throw new NoSuchElementException("Book not found with ID: " + id);
        }
    }

    @Override
    @Transactional
    public void updateBook(Long id, BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        bookRepository.save(book);
    }

    @Override
    public void confirmBook(Long id) {
        BookDto existBookDto = getBookById(id);
        existBookDto.setConfirm(true);

        Book book = new Book();
        BeanUtils.copyProperties(existBookDto, book);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        BookDto bookDtoById = getBookById(id);
        Book book = new Book();
        BeanUtils.copyProperties(bookDtoById, book);
        bookRepository.delete(book);
    }

    private BookDto mapToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setImageLink(book.getImageLink());
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
