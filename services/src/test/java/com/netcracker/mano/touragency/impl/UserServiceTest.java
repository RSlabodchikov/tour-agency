package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.*;
import com.netcracker.mano.touragency.interfaces.UserService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = UserServiceImpl.getInstance();

    @Captor
    private ArgumentCaptor<User> captor;


    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = RegistrationException.class)
    @SneakyThrows
    public void registrationWithExistingLogin() {

        when(userDAO.findCredentialsByLogin("simple")).thenReturn(new Credentials());
        Credentials credentials = new Credentials("simple", "password");
        User user = User.builder()
                .credentials(credentials)
                .build();
        userService.register(user);
    }

    @Test(expected = RegistrationException.class)
    @SneakyThrows
    public void registerUserWithBadParams() {
        when(userDAO.findCredentialsByLogin("login")).thenThrow(new EntityNotFoundException());
        when(userDAO.add(any())).thenThrow(new CannotCreateEntityException());
        User user = User.builder()
                .credentials(new Credentials("login", "password"))
                .name("name")
                .surname("surname")
                .build();
        userService.register(user);
        verify(userDAO, times(1)).add(any());
    }


    @Test
    @SneakyThrows
    public void registerUser() {
        when(userDAO.findCredentialsByLogin("simple")).thenThrow(new EntityNotFoundException());
        User user = User.builder()
                .name("qwerty")
                .surname("qwerty")
                .isBlocked(false)
                .credentials(new Credentials("simple", "password"))
                .build();
        userService.register(user);
        verify(userDAO, times(1)).add(captor.capture());
        verify(userDAO, times(1)).findCredentialsByLogin("simple");

        Assert.assertEquals(Role.CLIENT, captor.getValue().getRole());
    }

    @Test
    @SneakyThrows
    public void signIn() {
        Credentials credentials = new Credentials("login", "password");
        User user = User.builder()
                .credentials(credentials)
                .name("name")
                .surname("surname")
                .isBlocked(false)
                .build();
        when(userDAO.findUserByCredentials(credentials)).thenReturn(user);
        User user1 = userService.signIn(credentials);

        verify(userDAO, times(1)).findUserByCredentials(credentials);
        Assert.assertEquals(user, user1);
    }

    @Test
    @SneakyThrows
    public void update() {
        User user = User.builder()
                .name("name")
                .surname("surname")
                .credentials(new Credentials("qwerty", "qwerty"))
                .build();
        when(userDAO.update(user)).thenReturn(user);
        userService.update(user);
        verify(userDAO, times(1)).update(user);
    }

    @Test(expected = CannotUpdateEntityException.class)
    @SneakyThrows
    public void failOnUpdate() {
        when(userDAO.update(any())).thenThrow(new CannotUpdateEntityException());
        userService.update(new User());
        verify(userDAO, times(1)).update(any());

    }


    @Test
    @SneakyThrows
    public void findById() {
        User user = User.builder()
                .isBlocked(true)
                .name("name")
                .surname("surname")
                .build();
        when(userDAO.getById(1L)).thenReturn(user);
        User user1 = userService.findById(1L);
        Assert.assertEquals(user, user1);
        verify(userDAO, times(1)).getById(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void noUserWithThisId() {
        when(userDAO.getById(1L)).thenThrow(new EntityNotFoundException());
        userService.findById(1L);
        verify(userDAO, times(1)).getById(1L);
    }

    @Test
    public void getAllUsers() {
        when(userDAO.getAll()).thenReturn(new ArrayList<>());
        List<User> userList = userService.getAllUsers();
        Assert.assertEquals(0, userList.size());
        verify(userDAO, times(1)).getAll();
    }

    @Test
    @SneakyThrows
    public void blockUser() {
        User user = User.builder()
                .name("name")
                .isBlocked(false)
                .build();
        user.setId(1L);
        when(userDAO.getById(1L)).thenReturn(user);
        userService.blockUser(1L);
        verify(userDAO, times(1)).getById(1L);
        verify(userDAO, times(1)).update(any());
        Assert.assertEquals(true, user.getIsBlocked());
    }

    @Test
    @SneakyThrows
    public void unblockUser() {
        User user = User.builder()
                .name("name")
                .isBlocked(true)
                .build();
        user.setId(1L);
        when(userDAO.getById(1L)).thenReturn(user);
        userService.unblockUser(1L);
        verify(userDAO, times(1)).getById(1L);
        verify(userDAO, times(1)).update(any());
        Assert.assertEquals(false, user.getIsBlocked());
    }

    @Test
    @SneakyThrows
    public void changePassword() {
        String login = "login";
        String oldPassword = "password";
        String newPassword = "newPassword";
        Credentials credentials = new Credentials("login", "password");
        User user = User.builder()
                .credentials(credentials)
                .name("name")
                .surname("surname")
                .isBlocked(false)
                .build();
        when(userDAO.findUserByCredentials(any())).thenReturn(user);
        userService.changePassword(login, oldPassword, newPassword);
        verify(userDAO, times(1)).findUserByCredentials(any());
        verify(userDAO, times(1)).changePassword(anyString(), anyString());
    }

    @Test(expected = AuthorizationException.class)
    @SneakyThrows
    public void cannotChangePasswordWithWrongOldPassword() {
        String login = "login";
        String oldPassword = "password";
        String newPassword = "newPassword";
        when(userDAO.findUserByCredentials(any())).thenThrow(new AuthorizationException());
        userService.changePassword(login, oldPassword, newPassword);
        verify(userDAO, times(1)).findUserByCredentials(any());
    }
}