package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.interfaces.TourService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tour-agency/tours")
@Api
public class TourController {
    private TourService service;

    @Autowired
    public TourController(TourService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tour is deleted");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Tour tour) {

        return new ResponseEntity<>(service.create(tour), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity update(@RequestBody Tour tour) {
        return ResponseEntity.ok(service.update(tour));
    }
}
