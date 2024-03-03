package pl.milosz.booksmanagement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.booksmanagement.dto.BookDto;
import pl.milosz.booksmanagement.model.book.Book;
import pl.milosz.booksmanagement.model.book.Kind;
import pl.milosz.booksmanagement.service.BookService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookDto bookDto;

    @BeforeEach
    public void init() {
        bookDto = BookDto.builder().id(1L).title("Clean Code").publisher("Helion").author("Martin Robert C.")
                .kind(Kind.FANTASY).releaseDate("2009-03-01").isbn("9780132350884L").confirm(false).user(new User()).build();
    }
    @Test
    void getBooksNotConfirm_ShouldReturnOkStatusWhenListIsNotEmpty() throws Exception {
        List<BookDto> listOfBookWithoutConfirmDto = List.of(bookDto);
        when(bookService.getBooksNotConfirm()).thenReturn(listOfBookWithoutConfirmDto);

        mockMvc.perform(get("/adminBookApproval"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", bookService.getBooksNotConfirm()))
                .andExpect(view().name("admin/adminBookApproval"));
    }

    @Test
    void getBooksNotConfirm_ShouldReturnOkStatusWhenListIsEmpty() throws Exception {
        when(bookService.getBooksNotConfirm()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/adminBookApproval"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(view().name("admin/adminBookApproval"));
    }

    @Test
    void confirmBook_ShouldReturnRedirectionStatusWhenBookWasConfirm() throws Exception {
        bookDto.setConfirm(true);
        doNothing().when(bookService).confirmBook(bookDto.getId());

        mockMvc.perform(get("/confirmBook/" + bookDto.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminBookApproval"));
    }

    @Test
    void getConfirmBooks_ShouldReturnRedirectionStatusWhenListIsNotEmpty() throws Exception {
        List<BookDto> bookDtoList = List.of(bookDto, bookDto);
        when(bookService.getConfirmBooks()).thenReturn(bookDtoList);

        mockMvc.perform(get("/adminManagementBook"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", bookDtoList))
                .andExpect(view().name("admin/adminManagementBook"));
    }

    @Test
    void getConfirmBooks_ShouldReturnRedirectionStatusWhenListIsEmpty() throws Exception {
        when(bookService.getConfirmBooks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/adminManagementBook"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(view().name("admin/adminManagementBook"));
    }

    @Test
    void editBook_PageShouldReturnOkStatus() throws Exception {
        when(bookService.getBookById(bookDto.getId())).thenReturn(bookDto);

        mockMvc.perform(get("/updateBook/" + bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("kind", Kind.values()))
                .andExpect(model().attribute("book", bookService.getBookById(bookDto.getId())))
                .andExpect(view().name("admin/adminBookUpdate"));
    }

    @Test
    void updateBook_PageShouldRedirectStatus() throws Exception {
        BookDto existBookDto = bookDto;
        BookDto updatedBook = bookDto;
        updatedBook.setTitle("test");

        doNothing().when(bookService).updateBook(existBookDto.getId(), updatedBook);

        mockMvc.perform(post("/updateBook/" + existBookDto.getId())
                        .flashAttr("book", updatedBook))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminBookApproval"));
        verify(bookService).updateBook(existBookDto.getId(), updatedBook);
    }

    @Test
    void deleteBook_PageShouldRedirectStatus() throws Exception {
        doNothing().when(bookService).deleteBook(bookDto.getId());

        mockMvc.perform(get("/delete/" + bookDto.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminManagementBook"));
    }
}