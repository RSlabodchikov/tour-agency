package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Tour;

import java.util.List;

public interface TourService {
    Tour getById(Long id);

    Tour create(Tour tour);

    List<Tour> getAll();

    void delete(Long id);

    Tour update(Tour tour);
}
