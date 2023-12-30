package pl.milosz.booksmanagement.cotroller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.model.book.Rating;
import pl.milosz.booksmanagement.service.BookService;
import pl.milosz.booksmanagement.service.RatingService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getConfirmBooks(Model model) {
        List<BookDto> allBook = bookService.getConfirmBooks();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "visitor/listBooks";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/addBookForm")
    public String createBookForm(Model model) {
        model.addAttribute("kind", Kind.values());
        model.addAttribute("book", new Book());
        return "user/addBookForm";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addBookForm")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.saveBook(bookDto);
        return "redirect:/";
    }
}