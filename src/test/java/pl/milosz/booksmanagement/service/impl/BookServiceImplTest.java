package pl.milosz.booksmanagement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    private BookDto bookDto;
    private Book book;

    @BeforeEach
    public void init() {
        bookDto = BookDto.builder().title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind(Kind.FANTASY).releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(false).build();
        book = Book.builder().title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind(Kind.HISTORY).releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(false).build();
    }

    @Test
    void saveBook_shouldSaveBookSuccessfully() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        bookServiceImpl.saveBook(bookDto);

        // Assert
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookCaptor.capture());

        Book bookCaptorValue = bookCaptor.getValue();
        assertFalse(bookCaptorValue.isConfirm());
        assertEquals(bookDto.getTitle(), bookCaptorValue.getTitle());
    }

    @Test
    void getBooksNotConfirm_shouldReturnStreamOfBooksWithoutConfirmInDataBase() {
        List<Book> listOfBooks = List.of(book, book);

        when(bookRepository.getBooksNotConfirm()).thenReturn(listOfBooks);

        List<BookDto> booksNotConfirm = bookServiceImpl.getBooksNotConfirm();

        assertThat(booksNotConfirm).isNotNull();
        assertThat(booksNotConfirm.size()).isEqualTo(2);
        assertThat(booksNotConfirm.get(0).isConfirm()).isEqualTo(false);

        verify(bookRepository).getBooksNotConfirm();
    }

    @Test
    void getConfirmBooks_shouldReturnStreamOfBooksWithConfirmInDataBase() {
        book.setConfirm(true);
        List<Book> listOfBooks = List.of(book);
        when(bookRepository.getConfirmBooks()).thenReturn(listOfBooks);

        List<BookDto> booksWithConfirm = bookServiceImpl.getConfirmBooks();

        assertThat(booksWithConfirm).isNotNull();
        assertThat(booksWithConfirm.size()).isEqualTo(1);
        assertThat(booksWithConfirm.get(0).isConfirm()).isEqualTo(true);

        verify(bookRepository).getConfirmBooks();
    }

    @Test
    void getBookById_shouldReturnBookDtoById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        BookDto bookById = bookServiceImpl.getBookById(book.getId());

        assertThat(bookById).isNotNull();
        assertThat(bookById.getTitle()).isEqualTo("Clean Code");

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void updateBook_shouldUpdateBookSuccessfully() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookServiceImpl.updateBook(bookDto.getId(), bookDto);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void deleteBook_shouldDeleteBookSuccessfully() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        bookServiceImpl.deleteBook(book.getId());

        verify(bookRepository).findById(book.getId());
        verify(bookRepository).delete(book);
    }
}