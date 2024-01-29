package pl.milosz.booksmanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@Controller
public class AdminController {
    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/adminBookApproval")
    public String getBooksNotConfirm(Model model) {
        List<BookDto> allBook = bookService.getBooksNotConfirm();

        if (allBook.isEmpty()) {
            model.addAttribute("book", new BookDto());
        } else {
            model.addAttribute("books", allBook);
        }
        return "admin/adminBookApproval";
    }
    @GetMapping("/adminManagementBook")
    public String getConfirmBooks(Model model) {
        List<BookDto> allBookDto = bookService.getConfirmBooks();

        if (allBookDto.isEmpty()) {
            model.addAttribute("book", new BookDto());
        } else {
            model.addAttribute("books", allBookDto);
        }
        return "admin/adminManagementBook";
    }

    @GetMapping("/confirmBook/{id}")
    public String confirmBook(@PathVariable Long id) {
        bookService.confirmBook(id);
        return "redirect:/adminBookApproval";
    }

    @GetMapping("/updateBook/{id}")
    public String getFormForUpdateBook(@PathVariable Long id, Model model) {
        model.addAttribute("kind", Kind.values());
        model.addAttribute("book", bookService.getBookById(id));
        return "admin/adminBookUpdate";
    }

    @PatchMapping("/updateBook/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookDto bookDto) {
        bookService.updateBook(id, bookDto);
        return "redirect:/adminBookApproval";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/adminManagementBook";
    }
}
