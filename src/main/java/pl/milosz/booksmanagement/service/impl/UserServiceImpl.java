package pl.milosz.booksmanagement.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.repository.UserRepository;
import pl.milosz.booksmanagement.service.UserService;

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

}
