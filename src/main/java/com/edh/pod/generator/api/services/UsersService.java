package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersService {

    @Autowired
    private UserRepository userRepository;
}
