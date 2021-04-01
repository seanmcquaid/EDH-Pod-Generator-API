package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    List<User> findUserByUsername(@Param("username") String username);

    @Query(value = "insert into users(username, password) values (:username, :password) returning *", nativeQuery = true)
    List<User> addUser(@Param("username") String username, @Param("password") String password);
}
