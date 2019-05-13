package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<Tour,Long> {
}
