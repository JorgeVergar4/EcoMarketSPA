package com.ecomarketspa.EcoMarketSPA.Service;

import com.ecomarketspa.EcoMarketSPA.Dto.EmailDto;
import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Repository.UserRepository;
import com.ecomarketspa.EcoMarketSPA.Service.Impl.UserServiceImpl;
import com.ecomarketspa.EcoMarketSPA.Service.email.EmailProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private EmailProducer emailProducer;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        emailProducer = mock(EmailProducer.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, emailProducer, passwordEncoder);
    }

    @Test
    public void testRegisterUserSuccess() {
        String login = "juan123";
        String password = "claveSegura";
        String email = "juan@example.com";
        String address = "Calle Falsa 123";

        when(userRepository.findFirstByLogin(login)).thenReturn(Optional.empty());

        UserModel savedUser = new UserModel();
        savedUser.setLogin(login);
        savedUser.setPassword(password);
        savedUser.setEmail(email);
        savedUser.setAddress(address);

        when(userRepository.save(any(UserModel.class))).thenReturn(savedUser);

        UserModel result = userService.registerUser(login, password, email, address);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
        assertEquals(email, result.getEmail());

        verify(emailProducer, times(1)).sendEmail(any(EmailDto.class));
    }

    @Test
    public void testRegisterUserMissingFieldsThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(null, "pass", "mail", "addr"));

        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser("login", "", "mail", "addr"));
    }

    @Test
    public void testRegisterUserAlreadyExistsThrowsException() {
        String login = "repetido";
        when(userRepository.findFirstByLogin(login)).thenReturn(Optional.of(new UserModel()));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userService.registerUser(login, "pass", "mail", "addr"));

        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    public void testAuthenticateReturnsUserIfFoundAndPasswordMatches() {
        String login = "login";
        String rawPassword = "rawPass";
        String encodedPassword = "encodedPass";

        UserModel user = new UserModel();
        user.setLogin(login);
        user.setPassword(encodedPassword);

        when(userRepository.findFirstByLogin(login)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        UserModel result = userService.authenticate(login, rawPassword);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
    }

    @Test
    public void testAuthenticateReturnsNullIfPasswordDoesNotMatch() {
        String login = "login";
        String rawPassword = "wrongPass";
        String encodedPassword = "encodedPass";

        UserModel user = new UserModel();
        user.setLogin(login);
        user.setPassword(encodedPassword);

        when(userRepository.findFirstByLogin(login)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        UserModel result = userService.authenticate(login, rawPassword);

        assertNull(result);
    }

    @Test
    public void testAuthenticateReturnsNullIfUserNotFound() {
        when(userRepository.findFirstByLogin("login")).thenReturn(Optional.empty());

        UserModel result = userService.authenticate("login", "anyPass");

        assertNull(result);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new UserModel(), new UserModel()));

        List<UserModel> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUserById() {
        UserModel user = new UserModel();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserModel result = userService.getUserById(1L);

        assertNotNull(result);
    }

    @Test
    public void testUpdateUserCallsSave() {
        UserModel user = new UserModel();
        user.setLogin("actualizar");

        userService.updateUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserById() {
        userService.deleteUserById(5L);
        verify(userRepository, times(1)).deleteById(5);
    }
}
