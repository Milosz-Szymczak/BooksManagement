package pl.milosz.booksmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.booksmanagement.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    @Query(value = "SELECT * from books where confirm = true", nativeQuery = true)
    List<Book> getConfirmBooks();

    @Query(value = "SELECT * from books where confirm = false", nativeQuery = true)
    List<Book> getBooksNotConfirm();
}
