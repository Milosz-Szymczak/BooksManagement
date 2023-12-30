package pl.milosz.booksmanagement.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "authAndReg/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/added-books";
    }

    @GetMapping("/registration")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "authAndReg/registration";
    }

    @PostMapping("/form-add-user")
    public String formAddUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/tableOfBooks";
    }
}
