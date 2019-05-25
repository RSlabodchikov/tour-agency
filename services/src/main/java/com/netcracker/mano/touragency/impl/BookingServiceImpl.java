package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.BookingConverter;
import com.netcracker.mano.touragency.converter.TourConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.BookingDTO;
import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private CreditCardService creditCardService;

    private UserConverter userConverter;

    private TourServiceImpl tourService;

    private BookingRepository repository;

    private BookingConverter converter;

    private TourConverter tourConverter;

    private UserService userService;

    public BookingServiceImpl(UserConverter userConverter, CreditCardService creditCardService, TourServiceImpl tourService, BookingRepository repository, BookingConverter converter, TourConverter tourConverter, UserService userService) {
        this.creditCardService = creditCardService;
        this.userConverter = userConverter;
        this.tourService = tourService;
        this.repository = repository;
        this.converter = converter;
        this.tourConverter = tourConverter;
        this.userService = userService;
    }

    @Override
    public BookingDTO create(BookingDTO bookingDTO) {
        log.info("Trying to create booking :{}", bookingDTO);
        Booking booking = converter.convertToEntity(bookingDTO);
        TourDTO tourDTO;
        tourDTO = tourService.getById(booking.getTour().getId());
        if (tourDTO.getNumberOfClients() < booking.getNumberOfClients()) {
            throw new CannotCreateEntityException("Not enough vacant places in this tour");
        }
        booking.setTour(tourConverter.convertToEntity(tourDTO));
        booking.setUser(userConverter.convertToEntity(userService.findByLogin(bookingDTO.getLogin())));
        double totalPrice = tourDTO.getPrice() * booking.getNumberOfClients();
        booking.setTotalPrice(totalPrice);
        CreditCardDTO cardDTO = creditCardService.getById(bookingDTO.getLogin(), bookingDTO.getCardId());
        if (cardDTO.getBalance() < totalPrice)
            throw new CannotCreateEntityException("Not enough money on this card to create booking");
        double remainder = cardDTO.getBalance() - booking.getTotalPrice();
        cardDTO.setBalance(remainder);
        creditCardService.updateBalance(cardDTO);
        tourDTO.setNumberOfClients(tourDTO.getNumberOfClients() - booking.getNumberOfClients());
        tourService.update(tourDTO);
        return converter.convertToDTO(repository.save(booking));
    }

    @Override
    public void delete(Long id, String login) {
        log.info("Trying to delete booking with id :{}", id);
        if (!repository.existsByIdAndUser_Credentials_Login(id, login))
            throw new EntityNotFoundException("Cannot delete not existing entity");
        repository.delete(id);

    }

    @Override
    public List<BookingDTO> getAll(String login) {
        log.info("Trying to get all  user bookings ");
        List<Booking> bookings = repository.findAllByUser_Credentials_Login(login);
        if (bookings.size() == 0) {
            throw new EntityNotFoundException("User with this id have no bookings");
        }
        return bookings.stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO update(BookingDTO booking) {
        log.info("Trying to update booking :{}", booking);
        if (!repository.existsByIdAndUser_Credentials_Login(booking.getId(), booking.getLogin()))
            throw new EntityNotFoundException("Cannot find booking with such id");
        return converter.convertToDTO(repository.save(converter.convertToEntity(booking)));
    }

    @Override
    public BookingDTO findById(Long id, String login) {
        log.info("Trying go get booking by id :{}", id);
        Booking booking = repository.findByIdAndUser_Credentials_Login(id, login);
        if (booking == null) throw new EntityNotFoundException("Cannot find booking with this parameters");
        return converter.convertToDTO(booking);
    }

    @Override
    public List<BookingDTO> findAllByCategory(String login, String category) {
        log.info("Trying to get all bookings by category :{}", category);
        return repository.findAllByTour_Category_NameAndUser_Credentials_Login(category, login)
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }
}
