package pl.milosz.booksmanagement.service;


import org.springframework.security.core.userdetails.UserDetails;
import pl.milosz.booksmanagement.model.user.User;

public interface UserService {

    void saveUser(User user);

}
