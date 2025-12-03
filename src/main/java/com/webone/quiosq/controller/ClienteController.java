package com.webone.quiosq.controller;

import com.webone.quiosq.controller.request.ClienteRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cliente")
@AllArgsConstructor
public class ClienteController {

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody ClienteRequest createUserDto,
        Authentication authentication) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
