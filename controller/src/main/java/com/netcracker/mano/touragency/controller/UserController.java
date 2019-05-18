package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.interfaces.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/tour-agency/users")
@Api
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String role) {
        if (role != null) return ResponseEntity.ok(service.getAllUsersByRole(role));
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO user) {
        return new ResponseEntity<>(service.register(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.ok(service.update(user));
    }

    @PostMapping(value = "/block")
    public ResponseEntity block(@RequestParam(name = "id") Long id) {
        service.blockUser(id);
        return ResponseEntity.ok("User is blocked");

    }

    @PostMapping(value = "/unblock")
    public ResponseEntity unblock(@RequestParam(name = "id") Long id) {
        service.unblockUser(id);
        return ResponseEntity.ok("User is unblocked now");

    }
}
