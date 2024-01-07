package pl.milosz.booksmanagement.service.impl.googleBook;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.dto.googleBook.BookEntryMapDto;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;
import pl.milosz.booksmanagement.service.GoogleBookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class GoogleBookServiceImpl implements GoogleBookService {

    private final GoogleBookConnector googleBookConnector;

    public GoogleBookServiceImpl(GoogleBookConnector googleBookConnector) {
        this.googleBookConnector = googleBookConnector;
    }

    private final Map<String, GoogleBookDto> allGoogleBooks = new HashMap<>();

    @Override
    public List<BookEntryMapDto> getAllGoogleBooks(String title) {
        allGoogleBooks.clear();
        String titleWithoutSpace = title.replaceAll("\\s", "");

        googleBookConnector.tryConnectWithGoogleApi(allGoogleBooks, titleWithoutSpace);

        List<BookEntryMapDto> bookEntries = new ArrayList<>();
        for (Map.Entry<String, GoogleBookDto> entry : allGoogleBooks.entrySet()) {
            bookEntries.add(new BookEntryMapDto(entry.getKey(), entry.getValue()));
        }

        return bookEntries;
    }

    @Override
    public BookDto sendGoogleBookToCheck(String key) {
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
        return bookDto;
    }
}
