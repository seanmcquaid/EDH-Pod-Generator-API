package com.edh.pod.generator.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UsersController {

    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(){
        return ResponseEntity.ok().build();
    }
}
