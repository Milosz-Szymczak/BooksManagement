package pl.milosz.booksmanagement.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.repository.BooksRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BooksRepository booksRepository;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    private Book book;

    @BeforeEach
    public void init() {
        book = Book.builder().title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn(9780132350884L).build();
    }
    @Test
    void saveBook_returnVoid() {
        when(booksRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book addedBook = bookServiceImpl.saveBook(book);

        Assertions.assertThat(addedBook).isNotNull();
        Assertions.assertThat(addedBook.getTitle()).isEqualTo("Clean Code");
        verify(booksRepository).save(book);
    }


    @Test
    void getAllBook_returnListOfBooks() {
        List<Book> books = List.of(book, book);
        when(booksRepository.findAll()).thenReturn((books));

        List<Book> allBooks = bookServiceImpl.getAllBook();
        Assertions.assertThat(allBooks.size()).isEqualTo(2);
        verify(booksRepository).findAll();
    }

    @Test
    void getBookById_returnBook() {
        when(booksRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        Book bookById = bookServiceImpl.getBookById(book.getId());

        Assertions.assertThat(bookById).isEqualTo(book);
        Assertions.assertThat(bookById.getTitle()).isEqualTo("Clean Code");
        verify(booksRepository).findById(book.getId());
    }

    @Test
    void updateBook_returnVoid() {
        when(booksRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book addedBook = bookServiceImpl.saveBook(book);

        Assertions.assertThat(addedBook).isNotNull();
        Assertions.assertThat(addedBook.getTitle()).isEqualTo("Clean Code");
        verify(booksRepository).save(book);
    }
}