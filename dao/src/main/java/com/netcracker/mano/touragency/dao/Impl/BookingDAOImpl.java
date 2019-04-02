package com.netcracker.mano.touragency.dao.Impl;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;

import static com.netcracker.mano.touragency.constants.FileNames.BOOKING_FILENAME;

public class BookingDAOImpl extends CrudDAOImpl<Booking> implements BookingDAO {
    public BookingDAOImpl() {
        super(BOOKING_FILENAME);
    }
}
