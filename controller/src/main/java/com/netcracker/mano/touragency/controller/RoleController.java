package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/tour_agency/roles")
public class RoleController {
    private RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find role with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity findByName(@RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok(service.findByName(name));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Cannot find this role", HttpStatus.BAD_REQUEST);
        }
    }
}
