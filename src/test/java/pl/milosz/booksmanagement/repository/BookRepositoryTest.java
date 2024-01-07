package pl.milosz.booksmanagement.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void init() {
        book = Book.builder().title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind(Kind.HISTORY).releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(true).build();
    }

    @Test
    void getConfirmBooks() {
        bookRepository.save(book);

        List<Book> confirmBooks = bookRepository.getConfirmBooks();

        Assertions.assertThat(confirmBooks).isNotNull();
        Assertions.assertThat(confirmBooks.size()).isEqualTo(1);

    }

    @Test
    void getBooksNotConfirm() {
        book.setConfirm(false);
        bookRepository.save(book);

        List<Book> confirmBooks = bookRepository.getBooksNotConfirm();

        Assertions.assertThat(confirmBooks).isNotNull();
        Assertions.assertThat(confirmBooks.size()).isEqualTo(1);
    }

    @Test
    public void saveBook_returnNotNull (){
        Book savedBook = bookRepository.save(book);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    public void updateBook_returnBookByID () {
        bookRepository.save(book);

        book.setTitle("test");
        bookRepository.save(book);

        Assertions.assertThat(book.getTitle()).isEqualTo("test");
    }

    @Test
    public void deleteBook_returnVoid () {
        bookRepository.save(book);

        assertAll(() -> bookRepository.delete(book));
    }
}