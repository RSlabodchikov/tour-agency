package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking) throws CannotCreateEntityException;

    void delete(Long userId, Long bookingId) throws EntityNotFoundException;

    List<Booking> getAll(Long userId) throws EntityNotFoundException;

    Booking update(Booking booking) throws CannotUpdateEntityException;

    Booking find(Long userId, Long id) throws EntityNotFoundException;

    List<Booking> findAllByCategory(Long userId, String category);
}
