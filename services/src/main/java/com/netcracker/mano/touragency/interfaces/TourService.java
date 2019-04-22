package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface TourService {
    Tour getById(Long id) throws EntityNotFoundException;

    Tour create(Tour tour) throws CannotCreateEntityException;

    List<Tour> getAll();

    void delete(Long id);

    Tour update(Tour tour) throws CannotUpdateEntityException;
}
