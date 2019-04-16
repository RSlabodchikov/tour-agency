package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking);

    void delete(Long userId, Long bookingId);

    List<Booking> getAll(Long userId);

    Booking updateBooking(Booking booking);

    Booking findBooking(Long userId, Long id);

    List<Booking> findAllByCategory(Long userId, String category);
}
