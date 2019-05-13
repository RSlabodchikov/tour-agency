package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tour_agency/bookings")
public class BookingController {
    private BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }


    @GetMapping(value = "/all")
    public ResponseEntity getAll(@RequestParam(name = "userId") Long userId) {
        try {
            return ResponseEntity.ok(service.getAll(userId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User with this id have no bookings", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Booking booking) {
        try {
            return ResponseEntity.ok(service.create(booking));
        } catch (CannotCreateEntityException e) {
            return new ResponseEntity<>("Cannot create booking", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "userId") Long userId, @RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(service.find(userId, id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find booking", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "userId") Long userId, @RequestParam(name = "id") Long id) {
        try {
            service.delete(userId, id);
            return ResponseEntity.ok("Deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No bookings with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Booking booking) {
        try {
            return ResponseEntity.ok(service.update(booking));
        } catch (CannotUpdateEntityException e) {
            return new ResponseEntity<>("Cannot update booking", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/category")
    public ResponseEntity getAllByCategory(@RequestParam(name = "category") String category, @RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(service.findAllByCategory(userId, category));
    }

}
