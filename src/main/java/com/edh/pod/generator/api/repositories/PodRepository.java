package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.PodMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PodRepository extends JpaRepository<PodMember, Integer>{

    @Query(value = "insert into pods(owner, member, member_email, name) values (:owner, :member, :member_email, :name) returning *", nativeQuery = true)
    List<PodMember> addPodMember(@Param("owner") String owner, @Param("member") String member, @Param("member_email") String member_email, @Param("name") String name);

    @Query(value = "select * from pods where owner = :owner", nativeQuery = true)
    List<PodMember> getPods(@Param("owner") String owner);

    @Query(value = "select * from pods where owner = :owner and name = :name", nativeQuery = true)
    List<PodMember> getPodByOwnerAndName(@Param("owner") String owner, @Param("name") String name);
}
