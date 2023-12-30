package pl.milosz.booksmanagement.cotroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminBookApproval")
    public String getBooksNotConfirm(Model model) {
        List<BookDto> allBooks = bookService.getBooksNotConfirm();

        if (allBooks.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBooks);
        }
        return "admin/adminBookApproval";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/confirmBook/{id}")
    public String confirmBook(@PathVariable Long id, @ModelAttribute("book") Book book) {
        BookDto bookById = bookService.getBookById(id);
        bookById.setConfirm(true);
        bookService.updateBook(bookById);
        return "redirect:/adminBookApproval";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminManagementBook")
    public String getConfirmBooksForAdminManagement(Model model) {
        List<BookDto> allBook = bookService.getConfirmBooks();
        if (allBook.isEmpty()) {
            model.addAttribute("book", new Book());
        } else {
            model.addAttribute("books", allBook);
        }
        return "admin/adminManagementBook";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/updateBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "admin/adminBookUpdate";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateBook/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book) {
        BookDto existBook = bookService.getBookById(id);
        existBook.setImageLink(book.getImageLink());
        existBook.setTitle(book.getTitle());
        existBook.setAuthor(book.getAuthor());
        existBook.setKind(book.getKind());
        existBook.setIsbn(book.getIsbn());
        existBook.setLanguage(book.getLanguage());
        existBook.setPublisher(book.getPublisher());
        existBook.setReleaseDate(book.getReleaseDate());

        bookService.updateBook(existBook);
        return "redirect:/adminManagementBook";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/adminManagementBook";
    }
}
