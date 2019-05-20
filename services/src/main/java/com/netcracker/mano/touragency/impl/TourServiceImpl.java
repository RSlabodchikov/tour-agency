package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CategoryConverter;
import com.netcracker.mano.touragency.converter.TourConverter;
import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import com.netcracker.mano.touragency.interfaces.TourService;
import com.netcracker.mano.touragency.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourServiceImpl implements TourService {

    private TourRepository repository;

    private TourConverter converter;

    private CategoryService categoryService;

    private CategoryConverter categoryConverter;

    @Autowired
    public TourServiceImpl(TourRepository repository, TourConverter converter, CategoryService categoryService, CategoryConverter categoryConverter) {
        this.repository = repository;
        this.converter = converter;
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public TourDTO getById(Long id) {
        log.info("Trying to get tour by id :{}", id);
        Tour tour = repository.findOne(id);
        if (tour == null) throw new EntityNotFoundException("Cannot find tour with this id");
        return converter.convertToDTO(tour);
    }

    @Override
    public TourDTO create(TourDTO tourDTO) {
        log.info("Trying to create tour :{}", tourDTO);
        Tour tour = converter.convertToEntity(tourDTO);
        if (tour.getSettlementDate().compareTo(tour.getEvictionDate()) > 0) {
            throw new CannotCreateEntityException("Eviction date must be later then settlement date");
        }
        tour.setCategory(categoryConverter.convertToEntity(categoryService.getById(tourDTO.getCategoryId())));
        return converter.convertToDTO(repository.save(tour));
    }

    @Override
    public void delete(Long id) {
        log.info("Trying to delete tour");
        if (!repository.exists(id)) throw new EntityNotFoundException("Cannot delete tour with this id");
        repository.delete(id);
    }

    @Override
    public List<TourDTO> getAll() {
        log.info("Trying to get all tours");
        return repository.findAll()
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TourDTO update(TourDTO tour) {
        log.info("Trying to update tour:{}", tour);
        if (tour.getSettlementDate().compareTo(tour.getEvictionDate()) > 0) {
            throw new CannotUpdateEntityException("Eviction date must be later then settlement date");
        }
        return converter.convertToDTO(repository.save(converter.convertToEntity(tour)));
    }
}
