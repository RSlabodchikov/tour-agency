package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api
@RequestMapping(value = "/tour-agency/cards")
public class CreditCardController {
    private CreditCardService service;


    @Autowired
    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<CreditCardDTO>> getAllUserCards() {
        return ResponseEntity.ok(service.getAllClientCards());
    }

    @PostMapping
    public ResponseEntity<CreditCardDTO> create(@RequestBody @Valid CreditCardDTO creditCard) {
        return new ResponseEntity<>(service.create(creditCard), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<CreditCardDTO> update(@RequestBody @Valid CreditCardDTO creditCard) {
        return ResponseEntity.ok(service.updateBalance(creditCard));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Card is deleted");
    }

    @GetMapping(value = "/best")
    public ResponseEntity<CreditCardDTO> getBestCard() {
        return ResponseEntity.ok(service.getByGreatestBalance());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditCardDTO> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
