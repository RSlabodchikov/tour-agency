package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/tour_agency/users/authorization")
public class AuthorizationController {
    private UserService service;

    @Autowired
    public AuthorizationController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity findUserByCredentials(@RequestBody Credentials credentials) {
        try {
            return ResponseEntity.ok(service.signIn(credentials));
        } catch (AuthorizationException e) {
            return new ResponseEntity<>("Cannot find user with this login and password", HttpStatus.BAD_REQUEST);
        }
    }

}
