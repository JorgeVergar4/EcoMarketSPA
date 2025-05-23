package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserModel mockUser;

    @BeforeEach
    public void setup() {
        mockUser = new UserModel();
        mockUser.setId(1);
        mockUser.setLogin("testuser");
        mockUser.setPassword("testpass");
        mockUser.setEmail("test@example.com");
        mockUser.setAddress("123 Main St");
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerRequest"))
                .andExpect(view().name("register_page"));
    }

    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginRequest"))
                .andExpect(view().name("login_page"));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        when(userService.registerUser(anyString(), anyString(), anyString(), anyString())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("login", "testuser")
                        .param("password", "testpass")
                        .param("email", "test@example.com")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegisterFailure() throws Exception {
        when(userService.registerUser(anyString(), anyString(), anyString(), anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("login", "testuser")
                        .param("password", "testpass")
                        .param("email", "test@example.com")
                        .param("address", "123 Main St"))
                .andExpect(view().name("error_page"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        when(userService.authenticate(anyString(), anyString())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("login", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal_page"))
                .andExpect(model().attribute("userLogin", "testuser"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.authenticate(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("login", "testuser")
                        .param("password", "wrongpass"))
                .andExpect(view().name("error_page"));
    }

    @Test
    public void testListUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user_list"));
    }

    @Test
    public void testEditUserFormSuccess() throws Exception {
        when(userService.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("edit_user"));
    }

    @Test
    public void testEditUserFormFailure() throws Exception {
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andExpect(view().name("error_page"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        doNothing().when(userService).updateUser(any(UserModel.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/edit")
                        .param("id", "1")
                        .param("login", "testuser")
                        .param("password", "testpass")
                        .param("email", "test@example.com")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}