package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UsersController {

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        return ResponseEntity.ok().build();
    }
}
