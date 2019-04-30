package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.netcracker.mano.touragency.constants.FileNames.BOOKING_FILENAME;

public class BookingDAOImpl extends CrudDAOImpl<Booking> implements BookingDAO {
    @Value("${files.booking}")
    public static String filename;

    public BookingDAOImpl() {
        super(BOOKING_FILENAME);
    }

    @Override
    public List<Booking> getAllByCategory(String category) {
        return null;
    }

    @Override
    public List<Booking> getAllClientBookings(long clientId) {
        return null;
    }

    @Override
    public Booking findBookingByClientIdAndId(long id, long userId) throws EntityNotFoundException {
        return null;
    }
}
