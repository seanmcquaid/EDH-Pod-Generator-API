package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PodRepository extends JpaRepository<Pod, Integer>{

    @Query(value = "insert into pods(owner, member, member_email, spell_table_url, name) values (owner, member, member_email, spell_table_url, name) returning *", nativeQuery = true)
    List<Pod> addPodMember(@Param("owner") String owner, @Param("member") String member, @Param("member_email") String member_email, @Param("spell_table_url") String spell_table_url, @Param("name") String name);

    @Query(value = "select * from pods where owner = :owner", nativeQuery = true)
    List<Pod> getPods(@Param("owner") String owner);
}
