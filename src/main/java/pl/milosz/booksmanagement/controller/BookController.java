package pl.milosz.booksmanagement.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.security.service.UserService;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/selectKind")
    public String getFilteredBooks(@RequestParam(name = "kind", required = false) String selectedKind, Model model) {
        List<BookDto> filteredBooks;

        if (selectedKind != null && !selectedKind.isEmpty() && !selectedKind.equals("ALL_KINDS")) {
            Kind kind = Kind.valueOf(selectedKind);
            filteredBooks = bookService.getBooksByKind(kind);
        } else {
            filteredBooks = bookService.getConfirmBooks();
        }

        model.addAttribute("books", filteredBooks);
        model.addAttribute("kinds", Kind.values());

        return "visitor/listBooks";
    }

    @GetMapping("/")
    public String getConfirmBooks(Model model) {
        List<BookDto> allBookDto = bookService.getConfirmBooks();
        if (allBookDto.isEmpty()) {
            model.addAttribute("book", new BookDto());
        } else {
            model.addAttribute("books", allBookDto);
            model.addAttribute("kinds", Kind.values());
        }
        return "visitor/listBooks";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/addBookForm")
    public String createBookForm(Model model) {
        model.addAttribute("kind", Kind.values());
        model.addAttribute("book", new BookDto());
        return "user/addBookForm";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/addBookForm")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {

        Optional<User> loggedInUser = userService.findLoggedUser();
        loggedInUser.ifPresent(bookDto::setUser);

        bookService.saveBook(bookDto);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/profile")
    public String getBooksAddedByUser(Model model) {
        Optional<User> loggedUser = userService.findLoggedUser();

        if (loggedUser.isEmpty()) {
            model.addAttribute("book", new BookDto());
        } else {
            List<BookDto> allBookDto = bookService.getBooksAddedByUser(loggedUser.get().getUsername());
            model.addAttribute("books", allBookDto);
        }

        return "user/profile";
    }
}