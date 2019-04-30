package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BookingServiceImpl implements BookingService {


    private CreditCardService creditCardService;

    private BookingDAO bookingDAO;

    private TourServiceImpl tourService;

    @Autowired
    public BookingServiceImpl(CreditCardService creditCardService, BookingDAO bookingDAO, TourServiceImpl tourService) {
        this.creditCardService = creditCardService;
        this.bookingDAO = bookingDAO;
        this.tourService = tourService;
    }


    @Override
    public Booking create(Booking booking) throws CannotCreateEntityException {
        log.info("Trying to create booking :{}", booking);
        if (booking.getNumberOfClients() < 0) throw new CannotCreateEntityException();
        Tour tour;
        try {
            tour = tourService.getById(booking.getTourId());
        } catch (EntityNotFoundException e) {
            throw new CannotCreateEntityException();
        }
        if (tour.getNumberOfClients() < booking.getNumberOfClients()) {
            throw new CannotCreateEntityException();
        }
        double totalPrice = tour.getPrice() * booking.getNumberOfClients();
        booking.setTotalPrice(totalPrice);
        try {
            CreditCard card = creditCardService.getByGreatestBalance(booking.getUserId());
            if (card.getBalance() < totalPrice) {
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
        log.info("Tring to delete booking with id :{}", bookingId);
        find(userId, bookingId);
        bookingDAO.delete(bookingId);

    }

    @Override
    public List<Booking> getAll(Long userId) throws EntityNotFoundException {
        log.info("Trying to get all  user bookings ");
        List<Booking> bookings = bookingDAO.getAllClientBookings(userId);
        if (bookings.size() == 0) {
            throw new EntityNotFoundException();
        } else return bookings;
    }

    @Override
    public Booking update(Booking booking) throws CannotUpdateEntityException {
        log.info("Trying to update booking :{}", booking);
        return bookingDAO.update(booking);
    }

    @Override
    public Booking find(Long userId, Long id) throws EntityNotFoundException {
        log.info("Trying go get booking by id :{}", id);
        return bookingDAO.findBookingByClientIdAndId(id, userId);
    }

    public List<Booking> findAllByCategory(Long userId, String category) throws EntityNotFoundException {
        log.info("Trying to get all bookings by category :{}", category);
        return bookingDAO.getAllByCategory(category)
                .stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
