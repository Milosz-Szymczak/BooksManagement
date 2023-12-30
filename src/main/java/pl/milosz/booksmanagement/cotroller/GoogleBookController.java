package pl.milosz.booksmanagement.cotroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryDto;
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

    private Map<String, GoogleBookDto> allGoogleBooks;

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
        String titleWithoutSpace = title.replaceAll("\\s", "");

        allGoogleBooks = googleBookService.getAllGoogleBooks(titleWithoutSpace);

        List<BookEntryDto> bookEntries = new ArrayList<>();

        for (Map.Entry<String, GoogleBookDto> entry : allGoogleBooks.entrySet()) {
            bookEntries.add(new BookEntryDto(entry.getKey(), entry.getValue()));
        }

        model.addAttribute("bookEntries", bookEntries);
        return "user/googleBooks";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/googleBooks/{key}")
    public String sendGoogleBookToCheck(@PathVariable String key, Model model) {
        GoogleBookDto googleBookDto;
        BookDto bookDto = new BookDto();

        for (String book : allGoogleBooks.keySet()) {
            if (book.equals(key)) {
                googleBookDto = allGoogleBooks.get(book);
                String title = googleBookDto.getTitle() + googleBookDto.getSubTitle();
                bookDto.setTitle(title);

                List<String> authorsFromGoogle = googleBookDto.getAuthors();
                StringBuilder authors = new StringBuilder();
                for (String author : authorsFromGoogle) {
                    authors.append(author);
                    authors.append(" ");
                }

                bookDto.setImageLink(googleBookDto.getImageLink());
                bookDto.setPublisher(googleBookDto.getPublisher());
                bookDto.setAuthor(String.valueOf(authors));
                bookDto.setReleaseDate(googleBookDto.getPublishedDate());
                bookDto.setIsbn(googleBookDto.getIsbn());
                bookDto.setLanguage(googleBookDto.getLanguage());
                bookDto.setConfirm(false);
            }
        }
        model.addAttribute("book", bookDto);
        model.addAttribute("kind", Kind.values());
        return "user/addBookForm";
    }
}
