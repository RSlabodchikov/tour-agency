package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.TourDTO;
import com.netcracker.mano.touragency.interfaces.TourService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<TourDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TourDTO> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tour is deleted");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid TourDTO tour) {
        return new ResponseEntity<>(service.create(tour), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<TourDTO> update(@RequestBody @Valid TourDTO tour) {
        return ResponseEntity.ok(service.update(tour));
    }
}
