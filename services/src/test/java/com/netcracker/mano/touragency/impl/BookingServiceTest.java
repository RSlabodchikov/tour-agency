package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.TourService;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BookingServiceTest {

    @Mock
    private TourServiceImpl tourService;


    @Mock
    private CreditCardServiceImpl creditCardService;

    @Mock
    private BookingDAO bookingDAO;

    @Captor
    private ArgumentCaptor<Booking> captor;


    @InjectMocks
    private BookingService bookingService = BookingServiceImpl.getInstance();

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void clientHaveNoBookings() {
        when(bookingDAO.getAll()).thenReturn(new ArrayList<>());
        bookingService.getAll(1L);
        verify(bookingDAO, times(1)).getAll();
    }

    @Test
    @SneakyThrows
    public void getAllClientBookings() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        Booking booking1 = Booking.builder()
                .userId(2L)
                .build();
        List<Booking> list = new ArrayList<>();
        list.add(booking1);
        list.add(booking);
        when(bookingDAO.getAll()).thenReturn(list);
        List<Booking> result = bookingService.getAll(1L);
        verify(bookingDAO, times(1)).getAll();
        Assert.assertEquals(1, result.size());
    }

    @Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotGetBookingByWrongId() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        booking.setId(1L);
        List<Booking> list = new ArrayList<>();
        list.add(booking);
        when(bookingDAO.getAll()).thenReturn(list);
        bookingService.find(1L, 2L);
        verify(bookingDAO, times(1)).getAll();
    }

    @Test
    @SneakyThrows
    public void getById() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        booking.setId(1L);
        List<Booking> list = new ArrayList<>();
        list.add(booking);
        when(bookingDAO.getAll()).thenReturn(list);
        Booking booking1 = bookingService.find(1L, 1L);
        verify(bookingDAO, times(1)).getAll();
        Assert.assertEquals(booking, booking1);
    }

    @Test
    @SneakyThrows
    public void updateBooking() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        booking.setId(1L);
        when(bookingDAO.update(booking)).thenReturn(booking);
        bookingService.update(booking);
        verify(bookingDAO).update(captor.capture());
        Assert.assertEquals(booking, captor.getValue());
    }

    @Test
    @SneakyThrows
    public void getAllBookingsByCategory() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        booking.setId(1L);
        List<Booking> list = new ArrayList<>();
        list.add(booking);
        when(bookingDAO.getAllByCategory(anyString())).thenReturn(list);
        bookingService.findAllByCategory(1L, "GENERAL");
        verify(bookingDAO, times(1)).getAllByCategory("GENERAL");
    }

    @Test
    @SneakyThrows
    public void delete() {
        Booking booking = Booking.builder()
                .userId(1L)
                .build();
        booking.setId(1L);
        List<Booking> list = new ArrayList<>();
        list.add(booking);
        when(bookingDAO.getAll()).thenReturn(list);
        bookingService.delete(1L, 1L);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateBookingWithNegativeNumberOfClients() {
        Booking booking = Booking.builder()
                .userId(1L)
                .numberOfClients(-1)
                .build();
        bookingService.create(booking);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateBookingWithNotExistingTour() {
        Booking booking = Booking.builder()
                .userId(1L)
                .tourId(1L)
                .numberOfClients(1)
                .build();
        when(tourService.getById(1L)).thenThrow(new EntityNotFoundException());
        bookingService.create(booking);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateBookingWithWrongNumberOfClients() {
        Booking booking = Booking.builder()
                .userId(1L)
                .tourId(1L)
                .numberOfClients(5)
                .build();
        Tour tour = Tour.builder()
                .price(100D)
                .numberOfClients(4)
                .build();
        when(tourService.getById(1L)).thenReturn(tour);
        bookingService.create(booking);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateTourWithoutMoney() {
        Booking booking = Booking.builder()
                .userId(1L)
                .tourId(1L)
                .numberOfClients(1)
                .build();
        Tour tour = Tour.builder()
                .price(100D)
                .numberOfClients(20)
                .build();
        when(tourService.getById(1L)).thenReturn(tour);
        CreditCard creditCard = CreditCard.builder()
                .balance(10D)
                .build();
        when(creditCardService.getByGreatestBalance(1L)).thenReturn(creditCard);
        bookingService.create(booking);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateBookingWithoutCreditCard(){
        Booking booking = Booking.builder()
                .userId(1L)
                .tourId(1L)
                .numberOfClients(1)
                .build();
        Tour tour = Tour.builder()
                .price(100D)
                .numberOfClients(20)
                .build();
        when(tourService.getById(1L)).thenReturn(tour);
        when(creditCardService.getByGreatestBalance(1L)).thenThrow(new EntityNotFoundException());
        bookingService.create(booking);
    }

    @Test
    @SneakyThrows
    public void createBooking(){
        Booking booking = Booking.builder()
                .userId(1L)
                .tourId(1L)
                .numberOfClients(1)
                .build();
        Tour tour = Tour.builder()
                .price(100D)
                .numberOfClients(20)
                .build();
        when(tourService.getById(1L)).thenReturn(tour);
        CreditCard creditCard = CreditCard.builder()
                .balance(1000D)
                .build();
        when(creditCardService.getByGreatestBalance(1L)).thenReturn(creditCard);
        bookingService.create(booking);
    }


}