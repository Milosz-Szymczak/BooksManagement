package pl.milosz.booksmanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryMapDto;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.service.GoogleBookService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GoogleBookController {

    private final GoogleBookService googleBookService;

    public GoogleBookController(GoogleBookService googleBookService) {
        this.googleBookService = googleBookService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchGoogleBook")
    public String createBookForm() {
        return "user/searchGoogleBook";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/searchGoogleBook")
    public String redirectToUrl(@RequestParam("title") String title, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("title", title);
        return "redirect:/googleBooks";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/googleBooks")
    public String getAllGoogleBooks(Model model, @ModelAttribute("title") String title) throws IOException {
        List<BookEntryMapDto> allGoogleBooks = googleBookService.getAllGoogleBooks(title);

        model.addAttribute("bookEntries", allGoogleBooks);
        return "user/googleBooks";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/googleBooks/{key}")
    public String sendGoogleBookToCheck(@PathVariable String key, Model model) {
        BookDto bookDto = googleBookService.sendGoogleBookToCheck(key);

        model.addAttribute("book", bookDto);
        model.addAttribute("kind", Kind.values());
        return "user/addBookForm";
    }
}