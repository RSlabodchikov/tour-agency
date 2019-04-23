package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.impl.jdbc.TourDAOImplJDBC;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TourServiceImpl implements TourService {
    private TourDAOImplJDBC tourDAO;

    @Autowired
    public void setTourDAOImplJDBC(TourDAOImplJDBC tourDAO) {
        this.tourDAO = tourDAO;
    }

    @Override
    public Tour getById(Long id) throws EntityNotFoundException {
        log.debug("Trying to get tour by id :{}", id);
        return tourDAO.getById(id);
    }

    @Override
    public Tour create(Tour tour) throws CannotCreateEntityException {
        if (tour.getSettlementDate().compareTo(tour.getEvictionDate()) > 0
                || tour.getNumberOfClients() > Byte.MAX_VALUE) {
            throw new CannotCreateEntityException();
        }
        return tourDAO.add(tour);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        getById(id);
        tourDAO.delete(id);
    }

    @Override
    public List<Tour> getAll() {
        return tourDAO.getAll();
    }

    @Override
    public Tour update(Tour tour) throws CannotUpdateEntityException {
        return tourDAO.update(tour);
    }
}
