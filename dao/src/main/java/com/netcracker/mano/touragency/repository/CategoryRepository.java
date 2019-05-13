package com.netcracker.mano.touragency.repository;

import com.netcracker.mano.touragency.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
