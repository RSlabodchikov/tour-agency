package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.Impl.TourDAOImpl;
import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.interfaces.TourService;

import java.util.List;

public class TourServiceImpl implements TourService {
    private TourDAO tourDAO = new TourDAOImpl();

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
    public Tour getById(Long id) {
        return tourDAO.getById(id);
    }

    @Override
    public Tour create(Tour tour) {
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
    public Tour update(Tour tour) {
        return tourDAO.update(tour);
    }
}
