package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.entity.Booking;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;


public class BookingServiceTest {

    @Mock
    private TourServiceImpl tourService;

    @Mock
    private CreditCardServiceImpl creditCardService;

    @Captor
    private ArgumentCaptor<Booking> captor;


    @InjectMocks
    private BookingServiceImpl bookingService;

    @Before
    public void setUp() {
        initMocks(this);
    }

}