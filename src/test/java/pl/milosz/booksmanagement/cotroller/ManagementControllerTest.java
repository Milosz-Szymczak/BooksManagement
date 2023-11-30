package pl.milosz.booksmanagement.cotroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.Book;
import pl.milosz.booksmanagement.service.BookService;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ManagementController.class)
class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    public void init() {
        bookDto = BookDto.builder().id(1L).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn("9780132350884L").confirm(false).build();
        book = Book.builder().id(1L).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind("Computer Science").releaseDate("2009-03-01").isbn("9780132350884L").confirm(false).build();
    }

    @Test
    void getBooksForCheck_ShouldReturnOkStatusWhenListIsNotEmpty() throws Exception {
        List<BookDto> listOfBookWithoutConfirm = List.of(bookDto);
        when(bookService.getBooksWithoutConfirm()).thenReturn(listOfBookWithoutConfirm);

        mockMvc.perform(get("/management-check-book-admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", bookService.getBooksWithoutConfirm()))
                .andExpect(view().name("management-check-book-admin"));

    }

    @Test
    void getBooksForCheck_ShouldReturnOkStatusWhenListIsEmpty() throws Exception {
        when(bookService.getBooksWithoutConfirm()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/management-check-book-admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("management-check-book-admin"));
    }

    @Test
    void confirmBook_ShouldReturnRedirectionStatus() throws Exception {
        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);

        mockMvc.perform(get("/confirm-book/" + book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-check-book-admin"));
    }

    @Test
    void getAllConfirmBooksForAdmin_ShouldReturnOkStatusWhenListIsNotEmpty() throws Exception {
       bookDto.setConfirm(true);
        List<BookDto> listOfBookWithConfirm = List.of(bookDto);
        when(bookService.getBooksWithConfirm()).thenReturn(listOfBookWithConfirm);

        mockMvc.perform(get("/management-book-admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("management-book-admin"));
    }

    @Test
    void getAllConfirmBooksForAdmin_ShouldReturnOkStatusWhenListIsEmpty() throws Exception {
        when(bookService.getBooksWithConfirm()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/management-book-admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("management-book-admin"));
    }

    @Test
    void getAllConfirmBooks_ShouldReturnOkStatusWhenListIsNotEmpty() throws Exception {
        bookDto.setConfirm(true);
        List<BookDto> listOfBookWithConfirm = List.of(bookDto);
        when(bookService.getBooksWithConfirm()).thenReturn(listOfBookWithConfirm);

        mockMvc.perform(get("/added-books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("added-books"));
    }

    @Test
    void getAllConfirmBooks_ShouldReturnOkStatusWhenListIsEmpty() throws Exception {
        when(bookService.getBooksWithConfirm()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/added-books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("added-books"));
    }


    @Test
    void editBook_PageShouldReturnOkStatus() throws Exception {
        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);

        mockMvc.perform(get("/form-update-book/" + bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", bookService.getBookById(bookDto.getId())))
                .andExpect(view().name("form-update-book"));
    }

    @Test
    void updateBook_PageShouldRedirectStatus() throws Exception {
        BookDto existBook = bookDto;
        Book updatedBook = book;
        updatedBook.setTitle("test");

        when(bookService.getBookById(existBook.getId())).thenReturn(existBook);

        mockMvc.perform(post("/form-update-book/" + existBook.getId())
                        .flashAttr("book", updatedBook))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-book-admin"));

    }

    @Test
    void deleteBook_PageShouldRedirectStatus() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());

        mockMvc.perform(get("/delete/" + book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management-book-admin"));

    }

    @Test
    void createBookForm_PageShouldOkStatus() throws Exception {

        mockMvc.perform(get("/form-add-book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new Book()))
                .andExpect(view().name("form-add-book"));
    }

    @Test
    void saveBook_PageShouldRedirectStatus() throws Exception {
        when(bookService.saveBook(bookDto)).thenReturn(bookDto);

        mockMvc.perform(post("/form-add-book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/added-books"));
    }

}