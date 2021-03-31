package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query(value = "select user from users where id = :id", nativeQuery = true)
    Optional<User> findUserById(@Param("id") Integer id);
}
