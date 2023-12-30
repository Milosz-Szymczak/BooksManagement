package pl.milosz.booksmanagement.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.booksmanagement.model.user.User;


@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
}
