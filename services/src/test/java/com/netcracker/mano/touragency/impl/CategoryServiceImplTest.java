package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.CategoryConverter;
import com.netcracker.mano.touragency.dto.CategoryDTO;
import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository repository;

    @Mock
    CategoryConverter converter;

    private Category category;

    private CategoryDTO categoryDTO;

    @Before
    public void init() {
        initMocks(this);
        category = new Category(1L, "GENERAL");
        categoryDTO = new CategoryDTO(1L, "GENERAL");
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindById() {
        when(repository.findOne(anyLong())).thenReturn(null);
        service.getById(1L);
    }

    @Test
    public void findById() {
        when(repository.findOne(anyLong())).thenReturn(category);
        when(converter.convertToDTO(category)).thenReturn(categoryDTO);
        assertThat(service.getById(1L), is(categoryDTO));
    }

    @Test
    public void getAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(repository.findAll()).thenReturn(categories);
        when(converter.convertToDTO(any())).thenReturn(categoryDTO);
        assertThat(service.getAll().get(0), is(categoryDTO));
    }

    @Test
    public void update() {
        when(repository.save(category)).thenReturn(category);
        when(converter.convertToEntity(categoryDTO)).thenReturn(category);
        when(converter.convertToDTO(category)).thenReturn(categoryDTO);
        assertThat(service.update(categoryDTO), is(categoryDTO));
    }

    @Test(expected = EntityNotFoundException.class)
    public void cannotFindByName() {
        when(repository.findByName(anyString())).thenReturn(null);
        service.findByName("random");
    }

    @Test
    public void findByName() {
        when(repository.findByName("GENERAL")).thenReturn(category);
        when(converter.convertToDTO(category)).thenReturn(categoryDTO);
        assertThat(service.findByName("GENERAL"), is(categoryDTO));
    }

}