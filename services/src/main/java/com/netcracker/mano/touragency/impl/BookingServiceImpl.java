package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.dao.Impl.BookingDAOImpl;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.interfaces.TourService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private static BookingServiceImpl instance;

    public static BookingServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookingServiceImpl();
        }
        return instance;
    }

    private BookingServiceImpl() {
    }

    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public Booking createBooking(Booking booking) {
        if (booking.getNumberOfClients() < 0) return null;
        TourService tourService = TourServiceImpl.getInstance();
        Tour tour = tourService.getById(booking.getTourId());
        if (tour == null || tour.getNumberOfClients() < booking.getNumberOfClients()) {
            return null;
        }
        double totalPrice = tour.getPrice() * booking.getNumberOfClients();
        booking.setTotalPrice(totalPrice);
        CreditCardService creditCardService = CreditCardServiceImpl.getInstance();
        CreditCard card = creditCardService.getByGreatestBalance(booking.getUserId()).orElse(null);
        if (card == null || card.getBalance() < totalPrice) {
            return null;
        }
        double remainder = card.getBalance() - booking.getTotalPrice();
        creditCardService.updateBalance(card.getId(), remainder, booking.getUserId());
        tour.setNumberOfClients(tour.getNumberOfClients() - booking.getNumberOfClients());
        tourService.update(tour);
        bookingDAO.add(booking);
        return booking;
    }

    @Override
    public void delete(Long userId, Long bookingId) {
        if (findBooking(userId, bookingId) != null) {
            bookingDAO.delete(bookingId);
        }
    }

    @Override
    public List<Booking> getAll(Long userId) {
        return bookingDAO.getAll()
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookingDAO.update(booking);
    }

    @Override
    public Booking findBooking(Long userId, Long id) {
        Optional<Booking> booking = getAll(userId)
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        return booking.orElse(null);
    }
}
