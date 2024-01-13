package pl.milosz.booksmanagement.security.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static pl.milosz.booksmanagement.security.config.SecurityConfig.passwordEncoder;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setRole(User.Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        return Optional.ofNullable(userRepository.findByUsername(user));
    }

    @Override
    public Optional<User> checkAdminExist() {
        return userRepository.findByRole(pl.milosz.booksmanagement.model.user.User.Role.ADMIN);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Optional<pl.milosz.booksmanagement.model.user.User> admin = checkAdminExist();

            if (admin.isEmpty()) {
                pl.milosz.booksmanagement.model.user.User newAdmin = pl.milosz.booksmanagement.model.user.User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password"))
                        .role(pl.milosz.booksmanagement.model.user.User.Role.ADMIN)
                        .build();
                userRepository.save(newAdmin);
            }
        };
    }
}
