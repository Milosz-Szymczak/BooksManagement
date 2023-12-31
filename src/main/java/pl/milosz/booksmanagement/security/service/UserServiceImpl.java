package pl.milosz.booksmanagement.security.service;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.repository.UserRepository;

import java.util.List;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        user.setRole(User.Role.USER);
        userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
