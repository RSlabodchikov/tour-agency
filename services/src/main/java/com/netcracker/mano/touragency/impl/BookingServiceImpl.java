package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.dao.impl.jdbc.BookingDAOImplJDBC;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
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

    private BookingDAO bookingDAO = BookingDAOImplJDBC.getInstance();

    @Override
    public Booking create(Booking booking) throws CannotCreateEntityException {
        if (booking.getNumberOfClients() < 0) return null;
        TourService tourService = TourServiceImpl.getInstance();
        Tour tour;
        try {
            tour = tourService.getById(booking.getTourId());
        } catch (EntityNotFoundException e) {
            throw new CannotCreateEntityException();
        }
        if (tour == null || tour.getNumberOfClients() < booking.getNumberOfClients()) {
            throw new CannotCreateEntityException();
        }
        double totalPrice = tour.getPrice() * booking.getNumberOfClients();
        booking.setTotalPrice(totalPrice);
        try {
            CreditCardService creditCardService = CreditCardServiceImpl.getInstance();
            CreditCard card = creditCardService.getByGreatestBalance(booking.getUserId());
            if (card == null || card.getBalance() < totalPrice) {
                throw new CannotCreateEntityException();
            }
            double remainder = card.getBalance() - booking.getTotalPrice();

            creditCardService.updateBalance(card.getId(), remainder, booking.getUserId());
            tour.setNumberOfClients(tour.getNumberOfClients() - booking.getNumberOfClients());
            tourService.update(tour);
        } catch (CannotUpdateEntityException | EntityNotFoundException e) {
            throw new CannotCreateEntityException();
        }
        bookingDAO.add(booking);
        return booking;
    }

    @Override
    public void delete(Long userId, Long bookingId) throws EntityNotFoundException {
        findBooking(userId, bookingId);
        bookingDAO.delete(bookingId);

    }

    @Override
    public List<Booking> getAll(Long userId) {
        return bookingDAO.getAll()
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Booking updateBooking(Booking booking) throws CannotUpdateEntityException {
        return bookingDAO.update(booking);
    }

    @Override
    public Booking findBooking(Long userId, Long id) throws EntityNotFoundException {
        Optional<Booking> booking = getAll(userId)
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        if (booking.isPresent()) {
            return booking.get();
        } else throw new EntityNotFoundException();
    }

    public List<Booking> findAllByCategory(Long userId, String category) {
        return bookingDAO.getAllByCategory(category)
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
