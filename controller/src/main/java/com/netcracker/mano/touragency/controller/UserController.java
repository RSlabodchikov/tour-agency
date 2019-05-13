package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/tour_agency/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find user with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(service.register(user));
        } catch (RegistrationException e) {
            return new ResponseEntity<>("Invalid login or password", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    ResponseEntity update(@RequestBody User user) {
        try {
            return ResponseEntity.ok(service.update(user));
        } catch (CannotUpdateEntityException e) {
            return new ResponseEntity<>("Cannot update this user", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity block(@RequestParam(name = "id") Long id) {
        try {
            service.blockUser(id);
            return ResponseEntity.ok("User is blocked");
        } catch (EntityNotFoundException | CannotUpdateEntityException e) {
            return new ResponseEntity<>("Cannot block this user", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/unblock")
    ResponseEntity unblock(@RequestParam(name = "id") Long id) {
        try {
            service.unblockUser(id);
            return ResponseEntity.ok("User is unblocked now");
        } catch (EntityNotFoundException | CannotUpdateEntityException e) {
            return new ResponseEntity<>("Cannot unblock user with this id", HttpStatus.BAD_REQUEST);
        }
    }

}
