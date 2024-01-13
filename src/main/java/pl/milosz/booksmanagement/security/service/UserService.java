package pl.milosz.booksmanagement.security.service;


import pl.milosz.booksmanagement.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    List<User> getAllUsers();

    Optional<User> findLoggedUser();

    Optional<User> checkAdminExist();
}
