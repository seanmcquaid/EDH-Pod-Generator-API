package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        boolean doesUserExist = usersService.doesUserExist(user.getUsername());
        if(doesUserExist){
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        return ResponseEntity.ok().build();
    }
}
