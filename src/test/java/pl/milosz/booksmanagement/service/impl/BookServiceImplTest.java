package pl.milosz.booksmanagement.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.repository.BooksRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BooksRepository booksRepository;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    private BookDto bookDto;
    private Book book;

    @BeforeEach
    public void init() {
        bookDto = BookDto.builder().id(1L).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(true).build();
        book = Book.builder().id(2L).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(true).build();
    }

    @Test
    void saveBook_shouldUpdateBookSuccessfully() {
        when(booksRepository.save(any(Book.class))).thenReturn(book);

        BookDto savedBook = bookServiceImpl.saveBook(bookDto);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(bookDto);

        verify(booksRepository).save(any(Book.class));
    }

    @Test
    void getBooksWithoutConfirm_shouldReturnStreamOfBooksWithoutConfirmInDataBase() {
        Book bookWithoutConfirm = book;
        bookWithoutConfirm.setConfirm(false);
        List<Book> listOfBooks = List.of(bookWithoutConfirm,bookWithoutConfirm);

        when(booksRepository.findAll()).thenReturn(listOfBooks);

        List<BookDto> booksWithoutConfirm = bookServiceImpl.getBooksWithoutConfirm();

        assertThat(booksWithoutConfirm).isNotNull();
        assertThat(booksWithoutConfirm.size()).isEqualTo(2);
        assertThat(booksWithoutConfirm.get(0).isConfirm()).isEqualTo(false);

        verify(booksRepository).findAll();
    }

    @Test
    void getBooksWithConfirm_shouldReturnStreamOfBooksWithConfirmInDataBase() {
        List<Book> listOfBooks = List.of(book);
        when(booksRepository.findAll()).thenReturn(listOfBooks);

        List<BookDto> booksWithConfirm = bookServiceImpl.getBooksWithConfirm();

        assertThat(booksWithConfirm).isNotNull();
        assertThat(booksWithConfirm.size()).isEqualTo(1);
        assertThat(booksWithConfirm.get(0).isConfirm()).isEqualTo(true);

        verify(booksRepository).findAll();
    }

    @Test
    void getBookById_shouldReturnBookDtoById() {
        when(booksRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        BookDto bookById = bookServiceImpl.getBookById(book.getId());

        assertThat(bookById).isNotNull();
        assertThat(bookById.getTitle()).isEqualTo("Clean Code");

        verify(booksRepository).findById(book.getId());
    }

    @Test
    void updateBook_shouldUpdateBookSuccessfully() {
        when(booksRepository.save(any(Book.class))).thenReturn(book);

        bookServiceImpl.updateBook(bookDto);

        verify(booksRepository).save(any(Book.class));
    }

    @Test
    void deleteBook_shouldDeleteBookSuccessfully() {
        when(booksRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        bookServiceImpl.deleteBook(book.getId());

        verify(booksRepository).findById(book.getId());
        verify(booksRepository).delete(book);
    }
}