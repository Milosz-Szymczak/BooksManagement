package pl.milosz.booksmanagement.security.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.booksmanagement.model.user.User;
import pl.milosz.booksmanagement.security.config.SecurityConfig;
import pl.milosz.booksmanagement.security.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityConfig securityConfig;

    @Test
    void loginForm_ShouldReturnOkStatus() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authAndReg/login"));
    }

    @Test
    void logout_ShouldReturnRedirectionStatus() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void signUp_ShouldReturnOkStatusAndAddUserModel() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", new User()))
                .andExpect(view().name("authAndReg/registration"));
    }

    @Test
    void formRegistration() throws Exception {
        User user = new User();

        doNothing().when(userService).saveUser(user);
        doNothing().when(securityConfig).addNewUser(user);

        mockMvc.perform(post("/form-add-user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).saveUser(user);
        verify(securityConfig).addNewUser(user);
    }
}