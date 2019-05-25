package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CreditCardConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.CreditCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class CreditCardServiceTest {
    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Mock
    private CreditCardRepository repository;

    @Captor
    private ArgumentCaptor<CreditCard> captor;

    @Mock
    private UserService userService;

    @Mock
    private CreditCardConverter converter;

    @Mock
    private UserConverter userConverter;

    private CreditCard card;

    private CreditCardDTO cardDTO;

    @Before
    public void setUp() {
        card = CreditCard.builder()
                .id(1L)
                .balance(500)
                .user(User.builder()
                        .credentials(Credentials
                                .builder()
                                .login("login")
                                .build())
                        .build())
                .build();
        cardDTO = new CreditCardDTO();
        cardDTO.setLogin("login");
        cardDTO.setId(1L);
        cardDTO.setBalance(500);
        initMocks(this);
    }


    @Test
    public void getAll() {
        List<CreditCard> cards = new ArrayList<>();
        cards.add(card);
        when(repository.findAll()).thenReturn(cards);
        when(converter.convertToDTO(any())).thenReturn(cardDTO);
        assertThat(creditCardService.getAll().get(0), is(cardDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void clientHaveNoCards() {
        when(repository.findAllByUser_Credentials_Login(anyString())).thenReturn(new ArrayList<>());
        creditCardService.getAllClientCards("login");
    }

    @Test
    public void getAllClientCards() {
        List<CreditCard> cards = new ArrayList<>();
        cards.add(card);
        when(repository.findAllByUser_Credentials_Login("login")).thenReturn(cards);
        when(converter.convertToDTO(any())).thenReturn(cardDTO);
        assertThat(creditCardService.getAllClientCards("login").get(0), is(cardDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindCardById() {
        when(repository.findByIdAndUser_Credentials_Login(anyLong(), anyString())).thenReturn(null);
        creditCardService.getById("login", 1L);
    }

    @Test
    public void findUserCardById() {
        when(repository.findByIdAndUser_Credentials_Login(1L, "login")).thenReturn(card);
        when(converter.convertToDTO(card)).thenReturn(cardDTO);
        assertThat(creditCardService.getById("login", 1L), is(cardDTO));
    }

    @Test
    public void cannotCreate() {
        when(converter.convertToEntity(cardDTO)).thenReturn(card);
        when(converter.convertToDTO(card)).thenReturn(cardDTO);
        User user = User.builder().build();
        UserDTO userDTO = new UserDTO();
        when(userService.findByLogin("login")).thenReturn(userDTO);
        when(userConverter.convertToEntity(any())).thenReturn(user);
        when(repository.save(card)).thenReturn(card);
        assertThat(creditCardService.create(cardDTO),is(cardDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotDeleteNotExistingCard(){
        when(repository.existsByIdAndUser_Credentials_Login(1L,"login")).thenReturn(FALSE);
        creditCardService.delete(1L,"login");
    }

    @Test
    public void deleteCard(){
        when(repository.existsByIdAndUser_Credentials_Login(1L,"login")).thenReturn(TRUE);
        creditCardService.delete(1L,"login");
        verify(repository,times(1)).delete(1L);
    }

    @Test
    public void updateCardBalance(){
        when(repository.findByIdAndUser_Credentials_Login(1L,"login")).thenReturn(card);
        when(converter.convertToDTO(any())).thenReturn(cardDTO);
        when(repository.save(card)).thenReturn(card);
        when(converter.convertToEntity(any())).thenReturn(card);
        assertThat(creditCardService.updateBalance(cardDTO),is(cardDTO));

    }

    @Test
    public void getUserBestCard(){
        List<CreditCard> cards = new ArrayList<>();
        cards.add(card);
        when(repository.findAllByUser_Credentials_Login("login")).thenReturn(cards);
        when(converter.convertToDTO(any())).thenReturn(cardDTO);
        assertThat(creditCardService.getByGreatestBalance("login"),is(cardDTO));
    }


}