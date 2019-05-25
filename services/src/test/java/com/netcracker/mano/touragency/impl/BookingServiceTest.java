package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.BookingConverter;
import com.netcracker.mano.touragency.converter.TourConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.BookingDTO;
import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.*;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.BookingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class BookingServiceTest {

    @Mock
    private BookingRepository repository;

    @Mock
    private TourServiceImpl tourService;

    @Mock
    private CreditCardServiceImpl creditCardService;

    @Captor
    private ArgumentCaptor<Booking> captor;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private TourConverter tourConverter;

    @Mock
    private BookingConverter converter;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserService userService;

    private Booking booking;

    private BookingDTO bookingDTO;

    @Before
    public void setUp() {
        booking = Booking.builder()
                .id(1L)
                .numberOfClients(1)
                .user(User.builder()
                        .credentials(Credentials.builder()
                                .login("login")
                                .build())
                        .build())
                .card(CreditCard.builder()
                        .id(1L)
                        .balance(500)
                        .build())
                .tour(Tour.builder()
                        .id(1L)
                        .numberOfClients(2)
                        .price(100D)
                        .build())
                .build();
        bookingDTO = new BookingDTO();
        bookingDTO.setLogin("login");
        bookingDTO.setTourId(1L);
        bookingDTO.setId(1L);
        bookingDTO.setNumberOfClientsInTour(2);
        bookingDTO.setNumberOfClients(1);
        bookingDTO.setBalance(500D);
        initMocks(this);
    }


    @Test
    public void deleteBooking() {
        when(repository.existsByIdAndUser_Credentials_Login(1L, "login")).thenReturn(true);
        bookingService.delete(1L, "login");
        verify(repository, times(1)).delete(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotDeleteNotExistingBooking() {
        when(repository.existsByIdAndUser_Credentials_Login(1L, "login")).thenReturn(false);
        bookingService.delete(1L, "login");
    }

    @Test
    public void getAllClientBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        when(repository.findAllByUser_Credentials_Login("login")).thenReturn(bookings);
        when(converter.convertToDTO(any())).thenReturn(bookingDTO);
        assertThat(bookingService.getAll("login").get(0), is(bookingDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void userHaveNoBookings() {
        when(repository.findAllByUser_Credentials_Login(anyString())).thenReturn(new ArrayList<>());
        bookingService.getAll("login");
    }

    @Test
    public void updateBooking() {
        when(repository.existsByIdAndUser_Credentials_Login(1L, "login")).thenReturn(true);
        when(converter.convertToDTO(any())).thenReturn(bookingDTO);
        when(converter.convertToEntity(any())).thenReturn(booking);
        bookingService.update(bookingDTO);
        verify(repository, times(1)).save(captor.capture());
        assertThat(captor.getValue(), is(booking));

    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotUpdateBooking() {
        when(repository.existsByIdAndUser_Credentials_Login(1L, "login")).thenReturn(false);
        bookingService.update(bookingDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindById() {
        when(repository.findByIdAndUser_Credentials_Login(1L, "login")).thenReturn(null);
        bookingService.findById(1L, "login");
    }

    @Test
    public void findById() {
        when(repository.findByIdAndUser_Credentials_Login(1L, "login")).thenReturn(booking);
        when(converter.convertToDTO(booking)).thenReturn(bookingDTO);
        assertThat(bookingService.findById(1L, "login"), is(bookingDTO));
    }

    @Test
    public void findAllByCategory() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        when(repository.findAllByTour_Category_NameAndUser_Credentials_Login(anyString(), anyString())).thenReturn(bookings);
        when(converter.convertToDTO(any())).thenReturn(bookingDTO);
        assertThat(bookingService.findAllByCategory("login", "sport").get(0), is(bookingDTO));
    }

    @Test
    public void createBooking() {
        when(converter.convertToEntity(bookingDTO)).thenReturn(booking);
        when(converter.convertToDTO(any())).thenReturn(bookingDTO);
        TourDTO tourDTO = new TourDTO();
        tourDTO.setNumberOfClients(20);
        tourDTO.setPrice(100D);
        Tour tour = Tour.builder().price(100D).numberOfClients(20).build();
        when(tourService.getById(anyLong())).thenReturn(tourDTO);
        when(tourConverter.convertToEntity(tourDTO)).thenReturn(tour);
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userService.findByLogin(any())).thenReturn(userDTO);
        when(userConverter.convertToEntity(userDTO)).thenReturn(user);
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setBalance(500D);
        when(creditCardService.getById(any(), anyLong())).thenReturn(creditCard);
        assertThat(bookingService.create(bookingDTO), is(bookingDTO));

    }


}