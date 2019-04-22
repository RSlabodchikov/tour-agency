package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.dao.impl.jdbc.TourDAOImplJDBC;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.TourService;

import java.util.List;

public class TourServiceImpl implements TourService {
    private TourDAO tourDAO = TourDAOImplJDBC.getInstance();

    private static TourServiceImpl instance;

    private TourServiceImpl() {
    }

    public static TourServiceImpl getInstance() {
        if (instance == null) {
            instance = new TourServiceImpl();
        }
        return instance;
    }

    @Override
    public Tour getById(Long id) throws EntityNotFoundException {
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
    public void delete(Long id) {
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
