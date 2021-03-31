package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        boolean doesUserExist = usersService.doesUserExist(user.getUsername());
        if(doesUserExist){
            return ResponseEntity.status(401).build();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUserInfo = usersService.addUser(user.getUsername(), encodedPassword);

//        generate JWT and send in response


        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        return ResponseEntity.ok().build();
    }
}
