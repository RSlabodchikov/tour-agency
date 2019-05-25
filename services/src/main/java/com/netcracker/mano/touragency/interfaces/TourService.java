package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.TourDTO;

import java.util.List;

public interface TourService {
    TourDTO getById(Long id);

    TourDTO create(TourDTO tour);

    List<TourDTO> getAll();

    void delete(Long id);

    TourDTO update(TourDTO tour);
}
