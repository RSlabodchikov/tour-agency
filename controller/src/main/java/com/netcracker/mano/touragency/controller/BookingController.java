package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.BookingDTO;
import com.netcracker.mano.touragency.interfaces.BookingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api
@RequestMapping(value = "/tour-agency/bookings")
public class BookingController {
    private BookingService service;


    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BookingDTO booking) {
        return new ResponseEntity<>(service.create(booking), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted");

    }

    @PutMapping
    public ResponseEntity<BookingDTO> update(@RequestBody @Valid BookingDTO booking) {
        return ResponseEntity.ok(service.update(booking));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<BookingDTO>> getAllByCategory(@RequestParam(name = "category") String category) {
        return ResponseEntity.ok(service.findAllByCategory(category));
    }

}
