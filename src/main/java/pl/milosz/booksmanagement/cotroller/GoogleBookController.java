package pl.milosz.booksmanagement.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryDto;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;
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

    protected static Map<String, GoogleBookDto> allGoogleBooks;

    @GetMapping("/form-google-api")
    public String getTest() {
        return "form-google-api";
    }

    @PostMapping("/form-search-book/")
    public String redirectToUrl(@RequestParam("title") String title, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("title", title);
        return "redirect:/google-books";
    }

    @GetMapping("/google-books")
    public String getAllGoogleBooks(Model model, @ModelAttribute("title") String title) throws IOException {
        String titleWithoutSpace = title.replaceAll("\\s", "");

        allGoogleBooks = googleBookService.getAllGoogleBooks(titleWithoutSpace);

        List<BookEntryDto> bookEntries = new ArrayList<>();

        for (Map.Entry<String, GoogleBookDto> entry : allGoogleBooks.entrySet()) {
            bookEntries.add(new BookEntryDto(entry.getKey(), entry.getValue()));
        }

        model.addAttribute("bookEntries", bookEntries);
        return "google-books";
    }
}
