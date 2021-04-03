package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user){
        boolean doesUserExist = usersService.doesUserExist(user.getUsername());
        if(doesUserExist){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "This username has already been created, please try another one");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        String encodedPassword = usersService.encodePassword(user.getPassword());
        User newUserInfo = usersService.addUser(user.getUsername(), encodedPassword);

        String token = usersService.generateToken(newUserInfo.getUsername());

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user){
        boolean doesUserExist = usersService.doesUserExist(user.getUsername());
        if(!doesUserExist){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "This user doesn't exist, please try another one");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        User userInfo = usersService.getUserInfoByUsername(user.getUsername());
        boolean isPasswordCorrect = usersService.isPasswordCorrect(user.getPassword(), userInfo.getPassword());

        if(!isPasswordCorrect){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "This password isn't correct, please try another one");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        String token = usersService.generateToken(userInfo.getUsername());

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
