package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO create(BookingDTO booking);

    void delete(Long id, String login);

    List<BookingDTO> getAll(String login);

    BookingDTO update(BookingDTO booking);

    BookingDTO findById(Long id, String login);

    List<BookingDTO> findAllByCategory(String login, String category);
}
