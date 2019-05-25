package com.netcracker.mano.touragency.repository;


import com.netcracker.mano.touragency.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Boolean existsByIdAndUser_Credentials_Login(Long id, String login);

    List<Booking> findAllByUser_Credentials_Login(String login);

    Booking findByIdAndUser_Credentials_Login(Long id, String login);

    List<Booking> findAllByTour_Category_NameAndUser_Credentials_Login(String category, String login);
}
