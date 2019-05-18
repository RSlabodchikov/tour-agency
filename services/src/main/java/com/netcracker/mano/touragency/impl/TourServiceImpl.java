package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.TourService;
import com.netcracker.mano.touragency.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TourServiceImpl implements TourService {

    private TourRepository repository;

    @Autowired
    public TourServiceImpl(TourRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tour getById(Long id) {
        log.info("Trying to get tour by id :{}", id);
        Tour tour = repository.findOne(id);
        if (tour == null) throw new EntityNotFoundException("Cannot find tour with this id");
        return tour;
    }

    @Override
    public Tour create(Tour tour) {
        log.info("Trying to create tour :{}", tour);
        if (tour.getSettlementDate().compareTo(tour.getEvictionDate()) > 0) {
            throw new CannotCreateEntityException();
        }
        if (tour.getPrice() < 0) throw new CannotCreateEntityException();
        return repository.save(tour);
    }

    @Override
    public void delete(Long id) {
        log.info("Trying to delete tour");
        if (!repository.exists(id)) throw new EntityNotFoundException("Cannot delete tour with this id");
        repository.delete(id);
    }

    @Override
    public List<Tour> getAll() {
        log.info("Trying to get all tours");
        List<Tour> tours = new ArrayList<>();
        repository.findAll().forEach(tours::add);
        return tours;
    }

    @Override
    public Tour update(Tour tour) {
        log.info("Trying to update tour:{}", tour);
        if (tour.getSettlementDate().compareTo(tour.getEvictionDate()) > 0) {
            throw new CannotUpdateEntityException();
        }
        return repository.save(tour);
    }
}
