package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.CreditCardDTO;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import com.netcracker.mano.touragency.security.JwtTokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api
@RequestMapping(value = "/tour-agency/cards")
public class CreditCardController {
    private CreditCardService service;

    private JwtTokenUtil tokenUtil;

    @Autowired
    public CreditCardController(CreditCardService service, JwtTokenUtil tokenUtil) {
        this.service = service;
        this.tokenUtil = tokenUtil;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<CreditCardDTO>> getAllUserCards(HttpServletRequest req) {
        return ResponseEntity.ok(service.getAllClientCards(tokenUtil.getLoginFromRequest(req)));
    }

    @PostMapping
    public ResponseEntity<CreditCardDTO> create(@RequestBody @Valid CreditCardDTO creditCard, HttpServletRequest req) {
        creditCard.setLogin(tokenUtil.getLoginFromRequest(req));
        return new ResponseEntity<>(service.create(creditCard), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<CreditCardDTO> update(@RequestBody @Valid CreditCardDTO creditCard, HttpServletRequest req) {
        creditCard.setLogin(tokenUtil.getLoginFromRequest(req));
        return ResponseEntity.ok(service.updateBalance(creditCard));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "id") Long id, HttpServletRequest req) {
        service.delete(id, tokenUtil.getLoginFromRequest(req));
        return ResponseEntity.ok("Card is deleted");
    }

    @GetMapping(value = "/best")
    public ResponseEntity<CreditCardDTO> getBestCard(HttpServletRequest req) {
        return ResponseEntity.ok(service.getByGreatestBalance(tokenUtil.getLoginFromRequest(req)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditCardDTO> getById(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        return ResponseEntity.ok(service.getById(tokenUtil.getLoginFromRequest(req), id));
    }
}
