package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;

import java.util.List;

import static com.netcracker.mano.touragency.constants.FileNames.BOOKING_FILENAME;

public class BookingDAOImpl extends CrudDAOImpl<Booking> implements BookingDAO {
    public BookingDAOImpl() {
        super(BOOKING_FILENAME);
    }

    @Override
    public List<Booking> getAllByCategory(String category) {
        return null;
    }
}
