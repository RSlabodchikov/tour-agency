package com.netcracker.mano.touragency.interfaces;


import com.netcracker.mano.touragency.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getById(Long id);

    CategoryDTO update(CategoryDTO category);

    CategoryDTO findByName(String category);
}
