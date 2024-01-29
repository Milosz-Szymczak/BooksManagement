package pl.milosz.booksmanagement.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import pl.milosz.booksmanagement.repository.UserRepository;
import pl.milosz.booksmanagement.security.service.UserService;

import java.util.List;
import java.util.Optional;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final UserService userService;


    public SecurityConfig(InMemoryUserDetailsManager inMemoryUserDetailsManager, UserService userService) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.loginPage("/login"))
                .logout(form -> form.logoutRequestMatcher(new AntPathRequestMatcher("/log-out"))
                                .logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public void userDetailsService() {
        for (pl.milosz.booksmanagement.model.user.User user : userService.getAllUsers()) {
            UserDetails newUser = createUserDetailsFromUser(user);
            inMemoryUserDetailsManager.createUser(newUser);
        }
    }

    public void addNewUser(pl.milosz.booksmanagement.model.user.User user){
        UserDetails newUser = createUserDetailsFromUser(user);
        inMemoryUserDetailsManager.createUser(newUser);
    }

    private UserDetails createUserDetailsFromUser(pl.milosz.booksmanagement.model.user.User user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}