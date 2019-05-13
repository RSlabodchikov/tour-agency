package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Tour;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;


public class TourServiceTest {
    @Mock
    private TourDAO tourDAO;

    @Captor
    private ArgumentCaptor<Tour> captor;

    @InjectMocks
    private TourServiceImpl tourService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    /*@Test(expected = EntityNotFoundException.class)
    @SneakyThrows
    public void cannotGetByWrongId() {
        when(tourDAO.getById(anyLong())).thenThrow(new EntityNotFoundException());
        tourService.getById(1L);
        verify(tourDAO, times(1)).getById(1L);
    }

    @Test
    @SneakyThrows
    public void getTourById() {
        Tour tour = Tour.builder()
                .category(Category.GENERAL)
                .country("Belarus")
                .price(500D)
                .numberOfClients(5)
                .build();
        when(tourDAO.getById(anyLong())).thenReturn(tour);
        Assert.assertEquals(tour, tourService.getById(1L));
        verify(tourDAO, times(1)).getById(1L);
    }

    @Test(expected = CannotCreateEntityException.class)
    @SneakyThrows
    public void cannotCreateTourWithBadParams() {
        Tour tour = Tour.builder()
                .category(Category.GENERAL)
                .country("Belarus")
                .price(500D)
                .numberOfClients(5)
                .evictionDate(LocalDate.of(2019, 5, 5))
                .settlementDate(LocalDate.of(2019, 5, 6))
                .build();
        tourService.create(tour);
        verify(tourDAO, times(1)).add(any());
    }

    @Test
    @SneakyThrows
    public void createTour() {
        Tour tour = Tour.builder()
                .category(Category.GENERAL)
                .country("Belarus")
                .price(500D)
                .numberOfClients(5)
                .evictionDate(LocalDate.of(2019, 5, 5))
                .settlementDate(LocalDate.of(2019, 5, 3))
                .build();
        when(tourDAO.add(tour)).thenReturn(tour);
        tourService.create(tour);
        verify(tourDAO).add(captor.capture());
        Assert.assertEquals(tour, captor.getValue());
    }

    @Test
    @SneakyThrows
    public void deleteTour() {
        tourService.delete(1L);
        verify(tourDAO).delete(anyLong());
    }

    @Test
    @SneakyThrows
    public void getAllTours() {
        Tour tour = Tour.builder()
                .category(Category.GENERAL)
                .country("Belarus")
                .price(500D)
                .numberOfClients(5)
                .evictionDate(LocalDate.of(2019, 5, 5))
                .settlementDate(LocalDate.of(2019, 5, 3))
                .build();
        List<Tour> array = new ArrayList<>();
        array.add(tour);
        when(tourDAO.getAll()).thenReturn(array);
        Assert.assertEquals(1, tourService.getAll().size());
        verify(tourDAO, times(1)).getAll();
    }

    @Test(expected = CannotUpdateEntityException.class)
    @SneakyThrows
    public void cannotUpdateTour() {
        Tour tour = Tour.builder()
                .category(Category.GENERAL)
                .country("Belarus")
                .price(500D)
                .numberOfClients(5)
                .evictionDate(LocalDate.of(2019, 5, 5))
                .settlementDate(LocalDate.of(2019, 5, 3))
                .build();
        when(tourDAO.update(any())).thenThrow(new CannotUpdateEntityException());
        tourService.update(tour);
        verify(tourDAO, times(1)).update(any());
    }*/

}