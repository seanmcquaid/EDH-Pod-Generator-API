package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public boolean doesUserExist(String username){
        return userRepository.findUserByUsername(username).size() != 0;
    }
}
