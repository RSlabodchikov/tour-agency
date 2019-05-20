package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.RoleConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.RoleDTO;
import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.Role;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.repository.CredentialsRepository;
import com.netcracker.mano.touragency.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CredentialsRepository credentialsRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private UserConverter converter;

    @Mock
    private RoleConverter roleConverter;

    @Captor
    private ArgumentCaptor<User> captor;

    private User user;

    private UserDTO userDTO;

    @Before
    public void setUp() {
        initMocks(this);
        user = User.builder()
                .isBlocked(false)
                .credentials(Credentials.builder()
                        .login("login")
                        .password("password")
                        .build())
                .role(Role.builder()
                        .name("admin")
                        .build())
                .build();
        userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassword("password");
        userDTO.setIsBlocked(false);
        userDTO.setRole("admin");


    }

    @Test(expected = AuthorizationException.class)
    public void cannotFindUserByLogin() {
        when(userRepository.findByCredentials_Login(anyString())).thenReturn(null);
        userService.loadUserByUsername("Login");
        verify(userRepository, times(1)).findByCredentials_Login(anyString());
    }

    @Test(expected = AuthorizationException.class)
    public void cannotAuthorizeBlockedUser() {
        user.setIsBlocked(true);
        when(userRepository.findByCredentials_Login(anyString())).thenReturn(user);
        userService.loadUserByUsername("Login");
    }

    @Test
    public void canFindDetails() {
        when(userRepository.findByCredentials_Login(anyString())).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername(anyString());
        assertThat(user.getCredentials().getLogin(), is(userDetails.getUsername()));
        verify(userRepository, times(1)).findByCredentials_Login(anyString());

    }

    @Test(expected = RegistrationException.class)
    public void cannotRegisterUserWithExistingLogin() {
        userDTO.setLogin("login");
        when(credentialsRepository.existsCredentialsByLogin(anyString())).thenReturn(true);
        when(converter.convertToEntity(any())).thenReturn(user);
        userService.register(userDTO);
    }

    @Test
    public void registerUser() {
        when(converter.convertToEntity(any())).thenReturn(user);
        when(credentialsRepository.existsCredentialsByLogin(anyString())).thenReturn(false);
        when(roleService.findByName("client")).thenReturn(new RoleDTO(1, "admin"));
        when(roleConverter.convertToEntity(any())).thenReturn(new Role(1L, "admin"));
        when(converter.convertToDTO(any())).thenReturn(userDTO);
        assertThat(userDTO, is(userService.register(userDTO)));
    }

    @Test
    public void updateUser() {
        when(converter.convertToDTO(any())).thenReturn(userDTO);
        when(converter.convertToEntity(any())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        userService.update(userDTO);
        verify(userRepository, times(1)).save(captor.capture());
        assertThat(user, is(captor.getValue()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindUserById() {
        when(userRepository.findOne(anyLong())).thenReturn(null);
        userService.findById(1L);
        verify(userRepository, times(1)).findOne(anyLong());
    }

    @Test
    public void findById() {
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(converter.convertToDTO(user)).thenReturn(userDTO);
        assertThat(userDTO, is(userService.findById(1L)));
    }

    @Test
    public void getAll() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        when(converter.convertToDTO(any())).thenReturn(userDTO);
        assertThat(userService.getAll().get(0), is(userDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotBlockNotExistingUser() {
        when(userRepository.findOne(anyLong())).thenReturn(null);
        userService.block(1L);
    }

    @Test(expected = CannotUpdateEntityException.class)
    public void cannotBlockBlockedUser() {
        user.setIsBlocked(true);
        when(userRepository.findOne(anyLong())).thenReturn(user);
        userService.block(1L);
    }

    @Test
    public void blockUser() {
        user.setIsBlocked(false);
        userDTO.setIsBlocked(true);
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(converter.convertToDTO(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);
        userService.block(1L);
        verify(userRepository, times(1)).save(captor.capture());
        assertTrue(captor.getValue().getIsBlocked());
    }

    @Test
    public void unblockUser() {
        user.setIsBlocked(true);
        userDTO.setIsBlocked(false);
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(converter.convertToDTO(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);
        userService.unblock(1L);
        verify(userRepository, times(1)).save(captor.capture());
        assertFalse(captor.getValue().getIsBlocked());
    }

    @Test
    public void getAllByRole() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAllByRole_Name("admin")).thenReturn(users);
        when(converter.convertToDTO(any())).thenReturn(userDTO);
        assertThat(userService.getAllUsersByRole("admin").get(0), is(userDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindByLogin() {
        when(userRepository.findByCredentials_Login(anyString())).thenReturn(null);
        userService.findByLogin("login");
    }

    @Test
    public void findByLogin() {
        when(userRepository.findByCredentials_Login(anyString())).thenReturn(user);
        when(converter.convertToDTO(any())).thenReturn(userDTO);
        assertThat(userService.findByLogin("login"),is(userDTO));
    }

}