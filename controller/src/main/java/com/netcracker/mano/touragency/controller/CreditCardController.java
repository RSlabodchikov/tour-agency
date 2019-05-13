package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/tour_agency/cards")
public class CreditCardController {
    private CreditCardService service;

    @Autowired
    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll(@RequestParam(name = "userId", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.ok(service.getAll());
        } else {
            try {
                return ResponseEntity.ok(service.getAllClientCards(userId));
            } catch (EntityNotFoundException e) {
                return new ResponseEntity<>("No cards with this user id", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreditCard creditCard) {
        try {
            return ResponseEntity.ok(service.create(creditCard));
        } catch (CannotCreateEntityException e) {
            return new ResponseEntity<>("Cannot create card", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CreditCard creditCard) {
        try {
            return ResponseEntity.ok(service.updateBalance(creditCard));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Wrong id", HttpStatus.BAD_REQUEST);
        } catch (CannotUpdateEntityException e) {
            return new ResponseEntity<>("Wrong balance", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "cardId") Long cardId, @RequestParam(name = "userId") Long userId) {
        try {
            service.delete(cardId, userId);
            return ResponseEntity.ok("Card is deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot delete card", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/best")
    public ResponseEntity getBestCard(@RequestParam(name = "userId") Long userId) {
        try {
            return ResponseEntity.ok(service.getByGreatestBalance(userId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No cards with this userId", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "userId") Long userId, @RequestParam(name = "cardId") Long cardId) {
        try {
            return ResponseEntity.ok(service.getById(userId, cardId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Card not found", HttpStatus.BAD_REQUEST);
        }
    }
}
