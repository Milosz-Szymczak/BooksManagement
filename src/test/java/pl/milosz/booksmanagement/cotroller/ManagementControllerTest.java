package pl.milosz.booksmanagement.cotroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ManagementController.class)
class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void init() {
        book = Book.builder().id(1).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn(9780132350884L).build();
    }

    @Test
    void getAllBook() throws Exception {
        List<Book> listBooks = List.of(book, book);

        when(bookService.getAllBook()).thenReturn(listBooks);

        mockMvc.perform(get("/management-book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", listBooks))
                .andExpect(view().name("management-book"));
    }

    @Test
    void updateBook() throws Exception {
        Book existBook = book;
        Book updatedBook = book;
        updatedBook.setTitle("test");

        when(bookService.getBookById(existBook.getId())).thenReturn(existBook);

        mockMvc.perform(post("/form-update-book/" + existBook.getId())
                        .flashAttr("book", updatedBook))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-book"));

        verify(bookService).updateBook(existBook);
    }

    @Test
    void editBook() throws Exception {
        when(bookService.getBookById(book.getId())).thenReturn(book);

        mockMvc.perform(get("/form-update-book/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", bookService.getBookById(book.getId())))
                .andExpect(view().name("form-update-book"));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());

        mockMvc.perform(get("/delete/" + book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-book"));

        verify(bookService).deleteBook(book.getId());
    }

    @Test
    void createBookForm() throws Exception {

        mockMvc.perform(get("/form-add-book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new Book()))
                .andExpect(view().name("form-add-book"));
    }

    @Test
    void saveBook() throws Exception {
        when(bookService.saveBook(book)).thenReturn(book);

        mockMvc.perform(post("/form-add-book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-book"));
    }
}