package pl.milosz.booksmanagement.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.security.config.SecurityConfig;
import pl.milosz.booksmanagement.security.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final SecurityConfig securityConfig;

    public UserController(UserService userService, SecurityConfig securityConfig) {
        this.userService = userService;
        this.securityConfig = securityConfig;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "authAndReg/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "authAndReg/registration";
    }

    @PostMapping("/form-add-user")
    public String formRegistration(@ModelAttribute User user) {
        userService.saveUser(user);
        securityConfig.addNewUser(user);
        return "redirect:/login";
    }
}
