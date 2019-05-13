package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private CreditCardService creditCardService;

    private TourServiceImpl tourService;

    private BookingRepository repository;

    @Autowired
    public BookingServiceImpl(CreditCardService creditCardService, TourServiceImpl tourService, BookingRepository repository) {
        this.creditCardService = creditCardService;
        this.tourService = tourService;
        this.repository = repository;
    }

    @Override
    public Booking create(Booking booking) throws CannotCreateEntityException {
        log.info("Trying to create booking :{}", booking);
        if (booking.getNumberOfClients() < 0) throw new CannotCreateEntityException();
        Tour tour;
        try {
            tour = tourService.getById(booking.getTour().getId());
        } catch (EntityNotFoundException e) {
            throw new CannotCreateEntityException();
        }
        if (tour.getNumberOfClients() < booking.getNumberOfClients()) {
            throw new CannotCreateEntityException();
        }
        double totalPrice = tour.getPrice() * booking.getNumberOfClients();
        booking.setTotalPrice(totalPrice);
        try {
            CreditCard card = creditCardService.getById(booking.getUser().getId(), booking.getCard().getId());
            if (card.getBalance() < totalPrice) {
                throw new CannotCreateEntityException();
            }
            double remainder = card.getBalance() - booking.getTotalPrice();
            card.setBalance(remainder);
            creditCardService.updateBalance(card);
            tour.setNumberOfClients(tour.getNumberOfClients() - booking.getNumberOfClients());
            tourService.update(tour);
        } catch (CannotUpdateEntityException | EntityNotFoundException e) {
            throw new CannotCreateEntityException();
        }
        return repository.save(booking);
    }

    @Override
    public void delete(Long userId, Long bookingId) throws EntityNotFoundException {
        log.info("Trying to delete booking with id :{}", bookingId);
        if (!repository.existsByIdAndUser_Id(bookingId, userId)) throw new EntityNotFoundException();
        repository.delete(bookingId);

    }

    @Override
    public List<Booking> getAll(Long userId) throws EntityNotFoundException {
        log.info("Trying to get all  user bookings ");
        List<Booking> bookings = repository.findAllByUser_Id(userId);
        if (bookings.size() == 0) {
            throw new EntityNotFoundException();
        } else return bookings;
    }

    @Override
    public Booking update(Booking booking) throws CannotUpdateEntityException {
        log.info("Trying to update booking :{}", booking);
        if (booking.getNumberOfClients() < 0 || booking.getTotalPrice() < 0) throw new CannotUpdateEntityException();
        return repository.save(booking);
    }

    @Override
    public Booking find(Long userId, Long id) throws EntityNotFoundException {
        log.info("Trying go get booking by id :{}", id);
        Booking booking = repository.findByIdAndUser_Id(id, userId);
        if (booking == null) throw new EntityNotFoundException();
        return booking;
    }

    public List<Booking> findAllByCategory(Long userId, String category) {
        log.info("Trying to get all bookings by category :{}", category);
        return repository.findAllByTour_Category_NameAndUser_Id(category, userId);
    }
}
