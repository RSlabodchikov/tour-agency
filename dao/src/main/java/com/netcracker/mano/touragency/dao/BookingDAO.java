package com.netcracker.mano.touragency.dao;


import com.netcracker.mano.touragency.entity.Booking;

import java.util.List;

public interface BookingDAO extends CrudDAO<Booking> {
    List<Booking> getAllByCategory(String category);
}
