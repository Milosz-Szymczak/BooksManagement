package pl.milosz.booksmanagement.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@Controller
public class ManagementController {

    private final BookService bookService;

    public ManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/management-book")
    public String getAllBook(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "management-book";
    }

    @PostMapping("/form-update-book/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        Book existBook = bookService.getBookById(id);
        existBook.setTitle(book.getTitle());
        existBook.setAuthor(book.getAuthor());
        existBook.setKind(book.getKind());
        existBook.setIsbn(book.getIsbn());
        existBook.setPublisher(book.getPublisher());
        existBook.setReleaseDate(book.getReleaseDate());
        existBook.setAmount(book.getAmount());

        bookService.updateBook(existBook);
        return "redirect:/management-book";
    }

    @GetMapping("/form-update-book/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "form-update-book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/management-book";
    }

    @GetMapping("/form-add-book")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "form-add-book";
    }

    @PostMapping("/form-add-book")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/management-book";
    }

    @GetMapping("/shop")
    public String getShopTemplate(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "shop";
    }

}
