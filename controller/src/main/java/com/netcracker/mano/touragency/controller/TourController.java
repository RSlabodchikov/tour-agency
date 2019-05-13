package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tour_agency/tours")
public class TourController {
    private TourService service;

    @Autowired
    public TourController(TourService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find tour", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Tour is deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot delete this tour", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Tour tour) {
        try {
            return ResponseEntity.ok(service.create(tour));
        } catch (CannotCreateEntityException e) {
            return new ResponseEntity<>("Cannot create tour", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Tour tour) {
        try {
            return ResponseEntity.ok(service.update(tour));
        } catch (CannotUpdateEntityException e) {
            return new ResponseEntity<>("Cannot update tour", HttpStatus.BAD_REQUEST);
        }
    }
}
