package pl.milosz.booksmanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryMapDto;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.security.service.UserService;
import pl.milosz.booksmanagement.service.GoogleBookService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Controller
public class GoogleBookController {

    private final GoogleBookService googleBookService;
    private final UserService userService;

    public GoogleBookController(GoogleBookService googleBookService, UserService userService) {
        this.googleBookService = googleBookService;
        this.userService = userService;
    }

    @GetMapping("/searchGoogleBook")
    public String createBookForm() {
        return "user/searchGoogleBook";
    }

    @PostMapping("/searchGoogleBook")
    public String redirectToUrl(@RequestParam("title") String title, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("title", title);
        return "redirect:/googleBooks";
    }
    @GetMapping("/googleBooks")
    public String getAllGoogleBooks(Model model, @ModelAttribute("title") String title) throws IOException {
        List<BookEntryMapDto> allGoogleBooks = googleBookService.getAllGoogleBooks(title);

        model.addAttribute("bookEntries", allGoogleBooks);
        return "user/googleBooks";
    }
    @PostMapping("/googleBooks/{key}")
    public String sendGoogleBookToCheck(@PathVariable String key, Model model) {
        BookDto bookDto = googleBookService.sendGoogleBookToCheck(key);

        Optional<User> loggedInUser = userService.findLoggedUser();
        loggedInUser.ifPresent(bookDto::setUser);

        model.addAttribute("book", bookDto);
        model.addAttribute("kind", Kind.values());
        return "user/addBookForm";
    }
}