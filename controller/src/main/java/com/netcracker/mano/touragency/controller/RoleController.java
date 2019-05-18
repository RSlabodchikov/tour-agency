package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.RoleDTO;
import com.netcracker.mano.touragency.interfaces.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/tour-agency/roles")
@Api
public class RoleController {
    private RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll(HttpServletRequest req) {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "/name")
    public ResponseEntity<RoleDTO> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
}
