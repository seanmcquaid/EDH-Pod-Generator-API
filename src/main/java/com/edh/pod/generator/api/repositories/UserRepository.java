package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
}
