package pl.milosz.booksmanagement.cotroller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@Controller
public class ManagementController {

    private final BookService bookService;

    public ManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/management-check-book-admin")
    public String getBooksForCheck(Model model) {
        List<BookDto> allBook = bookService.getBooksWithoutConfirm();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "management-check-book-admin";
    }

    @GetMapping("/confirm-book/{id}")
    public String confirmBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        BookDto bookById = bookService.getBookById(id);
        bookById.setConfirm(true);
        bookService.updateBook(bookById);
        return "redirect:/management-check-book-admin";
    }

    @GetMapping("/management-book-admin")
    public String getAllConfirmBooksForAdmin(Model model) {
        List<BookDto> allBook = bookService.getBooksWithConfirm();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "management-book-admin";
    }

    @GetMapping("/added-books")
    public String getAllConfirmBooks(Model model) {
        List<BookDto> allBook = bookService.getBooksWithConfirm();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "added-books";
    }

    @GetMapping("/form-update-book/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "form-update-book";
    }

    @PostMapping("/form-update-book/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        BookDto existBook = bookService.getBookById(id);
        existBook.setTitle(book.getTitle());
        existBook.setAuthor(book.getAuthor());
        existBook.setKind(book.getKind());
        existBook.setIsbn(book.getIsbn());
        existBook.setLanguage(book.getLanguage());
        existBook.setPublisher(book.getPublisher());
        existBook.setReleaseDate(book.getReleaseDate());

        bookService.updateBook(existBook);
        return "redirect:/management-book-admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/management-book-admin";
    }

    @GetMapping("/form-add-book")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "form-add-book";
    }

    @PostMapping("/form-add-book")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.saveBook(bookDto);
        return "redirect:/added-books";
    }

}
