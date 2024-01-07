package pl.milosz.booksmanagement.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getConfirmBooks(Model model) {
        List<BookDto> allBookDto = bookService.getConfirmBooks();
        if (allBookDto.isEmpty()) {
            model.addAttribute("book", new BookDto());
        } else {
            model.addAttribute("books", allBookDto);
        }
        return "visitor/listBooks";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/addBookForm")
    public String createBookForm(Model model) {
        model.addAttribute("kind", Kind.values());
        model.addAttribute("book", new BookDto());
        return "user/addBookForm";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addBookForm")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.saveBook(bookDto);
        return "redirect:/";
    }
}