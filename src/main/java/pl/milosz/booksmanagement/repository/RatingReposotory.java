package pl.milosz.booksmanagement.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.booksmanagement.model.book.Rating;

@Repository
public interface RatingReposotory extends ListCrudRepository<Rating, Long> {
}
