package com.netcracker.mano.touragency.interfaces;


import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getById(Long id) throws EntityNotFoundException;

    Category update(Category category);
}
