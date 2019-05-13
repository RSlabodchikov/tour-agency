package com.netcracker.mano.touragency.impl;


import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import com.netcracker.mano.touragency.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(Long id) throws EntityNotFoundException {
        Category category = repository.findOne(id);
        if (category == null) throw new EntityNotFoundException();
        return category;
    }

    @Override
    public Category update(Category category) {
        return repository.save(category);
    }
}
