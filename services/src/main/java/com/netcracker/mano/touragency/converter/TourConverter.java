package com.netcracker.mano.touragency.converter;

import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.entity.Tour;
import org.springframework.stereotype.Component;

@Component
public class TourConverter {
    public Tour convertToEntity(TourDTO dto) {
        return Tour.builder()
                .id(dto.getId())
                .price(dto.getPrice())
                .numberOfClients(dto.getNumberOfClients())
                .settlementDate(dto.getSettlementDate())
                .evictionDate(dto.getEvictionDate())
                .country(dto.getCountry())
                .description(dto.getDescription())
                .category(Category.builder()
                        .id(dto.getCategoryId())
                        .build())
                .build();
    }

    public TourDTO convertToDTO(Tour tour) {
        return TourDTO.builder()
                .id(tour.getId())
                .categoryId(tour.getCategory().getId())
                .category(tour.getCategory().getName())
                .country(tour.getCountry())
                .description(tour.getDescription())
                .evictionDate(tour.getEvictionDate())
                .settlementDate(tour.getSettlementDate())
                .price(tour.getPrice())
                .numberOfClients(tour.getNumberOfClients())
                .build();
    }
}
