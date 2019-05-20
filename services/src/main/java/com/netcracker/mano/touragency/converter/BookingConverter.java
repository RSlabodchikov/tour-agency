package com.netcracker.mano.touragency.converter;


import com.netcracker.mano.touragency.dto.BookingDTO;
import com.netcracker.mano.touragency.entity.*;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    public BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBalance(booking.getCard().getBalance());
        bookingDTO.setCardId(booking.getCard().getId());
        bookingDTO.setNumber(booking.getCard().getNumber());

        bookingDTO.setCategory(booking.getTour().getCategory().getName());
        bookingDTO.setCategoryId(booking.getTour().getCategory().getId());
        bookingDTO.setCountry(booking.getTour().getCountry());
        bookingDTO.setEvictionDate(booking.getTour().getEvictionDate());
        bookingDTO.setSettlementDate(booking.getTour().getSettlementDate());
        bookingDTO.setTourId(booking.getTour().getId());
        bookingDTO.setPrice(booking.getTour().getPrice());
        bookingDTO.setDescription(booking.getTour().getDescription());
        bookingDTO.setNumberOfClientsInTour(booking.getTour().getNumberOfClients());


        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setNumberOfClients(booking.getNumberOfClients());
        bookingDTO.setId(booking.getId());

        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setUserName(booking.getUser().getName());
        bookingDTO.setUserSurname(booking.getUser().getSurname());
        bookingDTO.setRoleId(booking.getUser().getRole().getId());
        bookingDTO.setRole(booking.getUser().getRole().getName());
        bookingDTO.setIsBlocked(booking.getUser().getIsBlocked());
        bookingDTO.setLogin(booking.getUser().getCredentials().getLogin());
        bookingDTO.setPassword(booking.getUser().getCredentials().getPassword());
        bookingDTO.setCredentialsId(booking.getUser().getCredentials().getId());

        return bookingDTO;
    }

    public Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setNumberOfClients(bookingDTO.getNumberOfClients());
        booking.setUser(User.builder()
                .credentials(Credentials.builder()
                        .login(bookingDTO.getLogin())
                        .build())
                .build());
        booking.setCard(CreditCard.builder()
                .id(bookingDTO.getCardId())
                .build());
        booking.setTour(Tour.builder()
                .id(bookingDTO.getTourId())
                .build());
        return booking;
    }
}
