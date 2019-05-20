package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.BookingDTO;
import com.netcracker.mano.touragency.interfaces.BookingService;
import com.netcracker.mano.touragency.security.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api
@RequestMapping(value = "/tour-agency/bookings")
public class BookingController {
    private BookingService service;

    private JwtTokenUtil tokenUtil;

    @Autowired
    public BookingController(JwtTokenUtil jwtTokenUtil, BookingService service) {
        this.service = service;
        this.tokenUtil = jwtTokenUtil;
    }


    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAll(HttpServletRequest request) {

        return ResponseEntity.ok(service.getAll(tokenUtil.getLoginFromRequest(request)));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid BookingDTO booking) {
        return new ResponseEntity<>(service.create(booking), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return ResponseEntity.ok(service.findById(id, tokenUtil.getLoginFromRequest(request)));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
        service.delete(id, tokenUtil.getLoginFromRequest(request));
        return ResponseEntity.ok("Deleted");

    }

    @PutMapping
    public ResponseEntity<BookingDTO> update(@RequestBody @Valid BookingDTO booking) {
        return ResponseEntity.ok(service.update(booking));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<BookingDTO>> getAllByCategory(@RequestParam(name = "category") String category, HttpServletRequest request) {
        return ResponseEntity.ok(service.findAllByCategory(tokenUtil.getLoginFromRequest(request), category));
    }

}
