package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("")
    User findUserById(@Param("id") Integer id);
}
