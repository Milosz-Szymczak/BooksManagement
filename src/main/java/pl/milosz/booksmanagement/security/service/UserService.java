package pl.milosz.booksmanagement.security.service;


import pl.milosz.booksmanagement.model.user.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> getAllUsers();

}
