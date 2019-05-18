package com.netcracker.mano.touragency.impl;


import com.netcracker.mano.touragency.converter.CategoryConverter;
import com.netcracker.mano.touragency.dto.CategoryDTO;
import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import com.netcracker.mano.touragency.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    private CategoryConverter converter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, CategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = repository.findOne(id);
        if (category == null) throw new EntityNotFoundException("Cannot find category with this id");
        return converter.convertToDTO(category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        return converter.convertToDTO(repository.save(converter.convertToEntity(categoryDTO)));
    }


}
