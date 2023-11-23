package pl.milosz.booksmanagement.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.milosz.booksmanagement.model.Book;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    private Book book;

    @BeforeEach
    public void init() {
        book = Book.builder().title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn("9780132350884").language("pl").confirm(true).build();
    }

    @Test
    public void createBook_returnNotNull (){
        Book savedBook = booksRepository.save(book);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    public void updateBook_returnBookByID () {
        booksRepository.save(book);

        Optional<Book> checkBook = booksRepository.findById(1L);

        Assertions.assertThat(checkBook).isPresent();
    }

    @Test
    public void deleteBook_returnVoid () {
        booksRepository.save(book);

        assertAll(() -> booksRepository.delete(book));
    }
}