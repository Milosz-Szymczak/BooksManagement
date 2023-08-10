package pl.milosz.booksmanagement.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.booksmanagement.model.Book;

@Repository
public interface BooksRepository extends ListCrudRepository<Book, Long> {
}
