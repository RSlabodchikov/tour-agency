package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CategoryConverter;
import com.netcracker.mano.touragency.converter.TourConverter;
import com.netcracker.mano.touragency.dto.CategoryDTO;
import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import com.netcracker.mano.touragency.repository.TourRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class TourServiceTest {
    @InjectMocks
    private TourServiceImpl service;

    @Mock
    private TourRepository repository;

    @Mock
    private TourConverter converter;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryConverter categoryConverter;

    @Captor
    private ArgumentCaptor<Tour> captor;


    private Tour tour;

    private TourDTO tourDTO;

    @Before
    public void setUp() {
        initMocks(this);
        tour = Tour.builder()
                .id(1L)
                .category(new Category(1L, "GENERAL"))
                .evictionDate(Date.valueOf("2019-10-10"))
                .settlementDate(Date.valueOf("2019-10-5"))
                .build();
        tourDTO = new TourDTO();
        tourDTO.setCategory("GENERAL");
        tourDTO.setCategoryId(1L);
        tourDTO.setId(1L);
        tourDTO.setEvictionDate(Date.valueOf("2019-10-10"));
        tourDTO.setSettlementDate(Date.valueOf("2019-10-05"));
    }


    @Test(expected = EntityNotFoundException.class)
    public void cannotFindById() {
        when(repository.findOne(anyLong())).thenReturn(null);
        service.getById(1L);
    }

    @Test
    public void findById() {
        when(repository.findOne(1L)).thenReturn(tour);
        when(converter.convertToDTO(tour)).thenReturn(tourDTO);
        assertThat(service.getById(1L), is(tourDTO));
    }

    @Test(expected = CannotCreateEntityException.class)
    public void cannotCreateTour() {
        tour.setSettlementDate(Date.valueOf("2020-1-1"));
        when(converter.convertToEntity(tourDTO)).thenReturn(tour);
        service.create(tourDTO);
    }

    @Test
    public void createTour() {
        when(converter.convertToEntity(any())).thenReturn(tour);
        when(categoryService.getById(1L)).thenReturn(new CategoryDTO(1L, "GENERAL"));
        when(categoryConverter.convertToEntity(any())).thenReturn(new Category(1L, "GENERAL"));
        when(repository.save(tour)).thenReturn(tour);
        when(converter.convertToDTO(any())).thenReturn(tourDTO);
        service.create(tourDTO);
        verify(repository, times(1)).save(captor.capture());
        assertThat(captor.getValue(), is(tour));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotDelete() {
        when(repository.exists(anyLong())).thenReturn(false);
        service.delete(anyLong());
    }

    @Test
    public void delete() {
        when(repository.exists(anyLong())).thenReturn(true);
        service.delete(1L);
    }

    @Test
    public void getAll() {
        List<Tour> tours = new ArrayList<>();
        tours.add(tour);
        when(repository.findAll()).thenReturn(tours);
        when(converter.convertToDTO(any())).thenReturn(tourDTO);
        assertThat(service.getAll().get(0), is(tourDTO));
    }

    @Test(expected = CannotUpdateEntityException.class)
    public void cannotUpdate() {
        tourDTO.setEvictionDate(Date.valueOf("2019-05-05"));
        when(converter.convertToDTO(any())).thenReturn(tourDTO);
        when(converter.convertToEntity(any())).thenReturn(tour);
        service.update(tourDTO);
    }

    @Test
    public void update() {
        when(converter.convertToEntity(any())).thenReturn(tour);
        when(converter.convertToDTO(any())).thenReturn(tourDTO);
        assertThat(service.update(tourDTO), is(tourDTO));
    }
}