package com.netcracker.mano.touragency.interfaces;

import com.netcracker.mano.touragency.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO create(BookingDTO booking);

    void delete(Long id);

    List<BookingDTO> getAll();

    BookingDTO update(BookingDTO booking);

    BookingDTO findById(Long id);

    List<BookingDTO> findAllByCategory(String category);
}
