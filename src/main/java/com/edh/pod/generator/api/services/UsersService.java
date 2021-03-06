package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.repositories.UserRepository;
import com.edh.pod.generator.api.utils.JwtBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtBuilder jwtBuilder;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    public boolean doesUserExist(String username){
        return userRepository.findUserByUsername(username).size() != 0;
    }

    public User addUser(String username, String password){
        return userRepository.addUser(username, password).get(0);
    }

    public User getUserInfoByUsername(String username){
        return userRepository.findUserByUsername(username).get(0);
    }

    public String generateToken(String username){
        return jwtBuilder.generateToken(username);
    }

    public boolean isTokenValid(String token){
        return jwtBuilder.isTokenValid(token);
    }

    public Jws<Claims> decodeToken(String token){
        return jwtBuilder.decodeToken(token);
    }

    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isPasswordCorrect(String providedPassword, String passwordFromDb){
        return bCryptPasswordEncoder.matches(providedPassword, passwordFromDb);
    }
}
