package pl.milosz.booksmanagement.cotroller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Controller
public class ManagementController {

    private final BookService bookService;

    public ManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/management-check-book-admin")
    public String getBooksForCheck(Model model) {
        List<Book> allBook = bookService.getBooksWithoutConfirm();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "management-check-book-admin";
    }

    @GetMapping("/confirm-book/{id}")
    public String confirmBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        Book bookById = bookService.getBookById(id);
        bookById.setConfirm(true);
        bookService.updateBook(bookById);
        return "redirect:/management-check-book-admin";
    }

    @GetMapping("/management-book-admin")
    public String getAllConfirmBooksForAdmin(Model model) {
        List<Book> allBook = bookService.getBooksWithConfirm();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "management-book-admin";
    }

    @GetMapping("/added-books")
    public String getAllConfirmBooks(Model model) {
        List<Book> allBook = bookService.getBooksWithConfirm();
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
        Book existBook = bookService.getBookById(id);
        existBook.setTitle(book.getTitle());
        existBook.setAuthor(book.getAuthor());
        existBook.setKind(book.getKind());
        existBook.setIsbn(book.getIsbn());
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
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/added-books";
    }

    @GetMapping("/form-google-api")
    public String getTest(Model model) {
        return "form-google-api";
    }

    @PostMapping("/form-search-book")
    public String redirectToUrl(@RequestParam("name") URL name) throws IOException {
        try (InputStream input = name.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            String t;
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            reader.close();
            isr.close();

            t = json.toString();

            JsonObject jsonObject = JsonParser.parseString(t).getAsJsonObject();

            String title = jsonObject.getAsJsonObject("volumeInfo").get("title").getAsString();

            System.out.println(title);

        }
        return "form-google-api";
    }
}
