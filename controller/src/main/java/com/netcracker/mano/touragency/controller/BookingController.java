package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.interfaces.BookingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getAll(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Booking booking) {
        return new ResponseEntity<>(service.create(booking), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@RequestParam(name = "userId") Long userId, @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.find(userId, id));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "userId") Long userId, @RequestParam(name = "id") Long id) {
        service.delete(userId, id);
        return ResponseEntity.ok("Deleted");

    }

    @PutMapping
    public ResponseEntity update(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.update(booking));
    }

    @GetMapping(value = "/category")
    public ResponseEntity getAllByCategory(@RequestParam(name = "category") String category, @RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(service.findAllByCategory(userId, category));
    }

}
