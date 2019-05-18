package com.netcracker.mano.touragency.controller;


import com.netcracker.mano.touragency.dto.CredentialsDTO;
import com.netcracker.mano.touragency.entity.AuthToken;
import com.netcracker.mano.touragency.security.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/tour-agency/token")
@Api
public class AuthenticationController {

    private AuthenticationManager manager;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager manager, JwtTokenUtil jwtTokenUtil) {
        this.manager = manager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping
    public ResponseEntity<AuthToken> generateToken(@RequestBody @Valid CredentialsDTO credentialsDTO) {
        final Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentialsDTO.getLogin(),
                        credentialsDTO.getPassword()
                )
        );
        return ResponseEntity.ok(new AuthToken(jwtTokenUtil.generateToken(authentication)));
    }
}
