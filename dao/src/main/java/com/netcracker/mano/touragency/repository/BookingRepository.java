package com.netcracker.mano.touragency.repository;


import com.netcracker.mano.touragency.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Boolean existsByIdAndUser_Id(Long id, Long userId);

    List<Booking> findAllByUser_Id(Long userID);

    Booking findByIdAndUser_Id(Long bookingId, Long userId);

    List<Booking> findAllByTour_Category_NameAndUser_Id(String category, Long userID);
}
