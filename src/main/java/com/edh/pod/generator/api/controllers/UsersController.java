package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private final JwtBuilder jwtBuilder;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersController() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.jwtBuilder = new JwtBuilder();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user){
        boolean doesUserExist = usersService.doesUserExist(user.getUsername());
        if(doesUserExist){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "This username has already been created, please try another one");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUserInfo = usersService.addUser(user.getUsername(), encodedPassword);

        String token = jwtBuilder.generateToken(newUserInfo.getUsername());

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
        boolean isPasswordCorrect = bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword());

        if(!isPasswordCorrect){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "This password isn't correct, please try another one");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        String token = jwtBuilder.generateToken(userInfo.getUsername());

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
