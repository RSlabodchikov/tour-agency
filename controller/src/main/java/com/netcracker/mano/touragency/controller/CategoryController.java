package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Category;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/tour_agency/categories")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity update(@RequestBody Category category) {
        return ResponseEntity.ok(service.update(category));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find category with this id", HttpStatus.BAD_REQUEST);
        }
    }
}
