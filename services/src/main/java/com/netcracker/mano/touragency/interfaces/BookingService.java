package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking) throws CannotCreateEntityException;

    void delete(Long userId, Long bookingId);

    List<Booking> getAll(Long userId);

    Booking updateBooking(Booking booking) throws CannotUpdateEntityException;

    Booking findBooking(Long userId, Long id) throws EntityNotFoundException;

    List<Booking> findAllByCategory(Long userId, String category);
}
