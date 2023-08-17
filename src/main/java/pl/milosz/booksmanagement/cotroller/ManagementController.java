package pl.milosz.booksmanagement.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@Controller
public class ManagementController {

    private BookService bookService;

    public ManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/management")
    public String management(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "management";
    }

    @GetMapping("/addBook")
    public String saveBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "management";
    }
}
