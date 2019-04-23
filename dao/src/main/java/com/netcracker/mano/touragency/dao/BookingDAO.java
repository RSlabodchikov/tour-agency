package com.netcracker.mano.touragency.dao;


import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface BookingDAO extends CrudDAO<Booking> {
    List<Booking> getAllByCategory(String category) throws EntityNotFoundException;
}
