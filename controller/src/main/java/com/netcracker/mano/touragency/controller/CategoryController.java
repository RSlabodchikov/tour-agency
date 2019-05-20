package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.CategoryDTO;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/tour-agency/categories")
@Api
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid CategoryDTO category) {
        return new ResponseEntity<>(service.update(category), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<CategoryDTO> findByName(@PathVariable(name = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
}
